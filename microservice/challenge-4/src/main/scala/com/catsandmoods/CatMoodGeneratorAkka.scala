package com.catsandmoods

import akka.actor.{Actor, ActorSystem, Props}
import com.catsandmoods.support.CassandraSupport
import org.joda.time.DateTime

import scala.collection.parallel.ParSeq
import scala.concurrent.duration._

/**
  * cats-and-moods
  *
  */
object CatMoodGeneratorAkka {

  lazy val session = CassandraSupport.cluster.connect("cats")

  def main(args: Array[String]) {

    val nbCats = 1000
    val delay = 27

    val myCats = (1 to nbCats).map(num => Cat(num)).par
    implicit val system: ActorSystem = ActorSystem("scheduler")

    val triggerActor = system.actorOf(Props(classOf[TriggerActor], myCats))

    import system.dispatcher

    system.scheduler.schedule(
      0 milliseconds,
      delay seconds,
      triggerActor,
      Trigger)


  }

  val Trigger = "trigger"

  class TriggerActor(val pet: ParSeq[Cat]) extends Actor {
    def receive = {
      case Trigger ⇒ {
        //FixMe bulk load instead, prepareStatement
        // TODO handle error when connecting to cassandra
        // TODO Future ?
        val date =  DateTime.now()
        val month = date.getMonthOfYear
        val year = date.getYear
        val day = date.getDayOfMonth()
        val hour = date.getHourOfDay()
        val minute = date.getMinuteOfHour()
        val second = date.getSecondOfMinute()
        pet.foreach( cat => session.execute(s"Insert into cats.moods ( name, mood, year, month, day, hour, minute, second) Values ('${cat.name}', '${cat.mood}', $year, $month, $day, $hour, $minute, $second) "))
      }
    }
  }

}
