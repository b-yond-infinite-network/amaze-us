package com.byond

import akka.actor.typed.ActorRef
import akka.actor.typed.ActorSystem
import akka.actor.typed.Behavior
import akka.actor.typed.scaladsl.Behaviors
import akka.actor.typed.scaladsl.ActorContext

import scala.collection.mutable.ArrayBuffer
import java.util.Date
import slick.jdbc.H2Profile.api._
import slick.jdbc.H2Profile.backend.DatabaseDef

import scala.concurrent.duration._
import scala.concurrent.Await
import scala.concurrent.ExecutionContext.Implicits.global


object RootActor {
  def insertMoods(cats: TableQuery[Cats], db: DatabaseDef, catStates: ArrayBuffer[CatState]): Unit = {    
    val newRows = catStates.map({ catState =>
      val CatState(CatId(id), mood, timestamp) = catState
      (id, Cat.moodToString(mood), timestamp)
    })
    val insertAction = DBIO.seq(cats ++= newRows) // Batch inserts are required for performance
    val insertFuture = db.run(insertAction)
    Await.result(insertFuture, Duration.Inf)
  }

  def spawnCats(context: ActorContext[Response], catNumber: Int, catChangeInterval: FiniteDuration, startTimestamp: Long): ArrayBuffer[ActorRef[Command]] = {
      var children: ArrayBuffer[ActorRef[Command]] = new ArrayBuffer()
      for (i <- 1 to catNumber) {
        val child = context.spawn(Cat(CatState(CatId(i), CatMiaw, startTimestamp), context.self, catChangeInterval), "cat" + i.toString())
        children += child
      }
      children
  }

  def fetchStatistics(cats: TableQuery[Cats], db: DatabaseDef): StatisticsSummary = {
    val q = cats.map(x => (x.id, x.mood, x.timestamp))
    val states: Seq[CatState] = Await.result(db.run(q.result), Duration.Inf).flatMap({
      case (i, m, t) => Cat.moodFromString(m).map(mood => CatState(CatId(i), mood, t))
    })
    Stats.summarize(states)
  }

  def currentTimestamp(): Long = {
    new Date().getTime() / 1000
  }

  def apply(catNumber: Int, catChangeInterval: FiniteDuration, refreshInterval: FiniteDuration, modelDuration: FiniteDuration): Behavior[Response] = {
    val startTimestamp = currentTimestamp()
    val maxTimestamp = startTimestamp + modelDuration.toSeconds

    // Initialize the in-memory database
    val db = Database.forConfig("h2mem")
    val cats = TableQuery[Cats]
    Await.result(db.run(DBIO.seq(cats.schema.create)), Duration.Inf)

    // Define the behavior of the Root actor:
    //  - Create and destroy the cat actors
    //  - Receive messages that represent the mood of cats, and write them to the database by batches
    //  - Periodically calculate the statistics of the cat moods, from the beginning of the simulation
    Behaviors.withTimers { timers => 
      timers.startTimerWithFixedDelay(KillCats(), modelDuration)
      timers.startTimerWithFixedDelay(RefreshStatistics(), refreshInterval)
      Behaviors.setup { context =>

        // Start the cats
        val children = spawnCats(context, catNumber, catChangeInterval, startTimestamp)

        var messageN = 0
        var catsKilled = false
        var catStateBuffer: ArrayBuffer[CatState] = new ArrayBuffer()
        Behaviors.receiveMessage {
          message => message match {
            case ResponseAddCatState(catState) => {
              messageN = messageN + 1
              if (messageN % 1000 == 0) { println("Number of cat moods generated: " + messageN.toString()) }
              catStateBuffer += catState
              if (catStateBuffer.size >= 5000) {
                insertMoods(cats, db, catStateBuffer)
                catStateBuffer = new ArrayBuffer()
              }
            }
            case KillCats() => {
              if (currentTimestamp() >= maxTimestamp && !catsKilled) {
                println("Closing the cat actors")
                children.foreach { c => context.stop(c) }
                catsKilled = true
              }
            }
            case RefreshStatistics() => {
              // Flush to database
              insertMoods(cats, db, catStateBuffer)
              catStateBuffer = new ArrayBuffer()
              
              println("\nUpdating summary statistics:")
              val stats = fetchStatistics(cats, db)
              println(" Number of cat moods in database: " + stats.total.toString())
              for ((mood, count) <- stats.counts.iterator) {
                println(" Number of occurrences of \"" ++ Cat.moodToString(mood) ++ "\": " ++ count.toString())
              }
              println("")

              // 2 seconds of leniency to make sure that the latest cat states have been written
              if (currentTimestamp() >= maxTimestamp + 2) {
                println("Closing the database and the actor system")
                db.close()
                context.system.terminate()
              }
            }
          }
          Behaviors.same
        }     
      }
    }
  }

}




// The cat simulation is real-time. Cats are represented by Akka actors
// A non real-time model could generate the same data and statistics, but it wouldn't be as fun
// Since the cat's behavior is a simple Markov model and the statistics are basic, the bottleneck is in the persistence layer.
// To simulate a much larger group of cats, we would need to use multiple computers and databases, calculate statistics on each database, and combine these statistics (function Stats.combine). This assumes that the statistics of different cat groups can be combined (i.e they form a semigroup). Otherwise we can't scale orthogonally to the number of cats
object Main extends App {
    println("Starting the cat simulation")
    val catNumber = 1000
    val (catChangeInterval, refreshInterval, modelDuration) = (50.millis, 3.seconds, 10.seconds)   // Run the model for 10 seconds
    //val (catChangeInterval, refreshInterval, modelDuration) = (27.seconds, 1.minute, 1.day)      // Run one full day with a new mood every 27 seconds
    val root: ActorSystem[Nothing] = ActorSystem(RootActor(catNumber, catChangeInterval, refreshInterval, modelDuration), "ClowderSystem")
}