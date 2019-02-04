package simucat

import scala.util.Random
import scala.concurrent.duration._
import akka.actor.{Actor, Timers, ActorRef}

/** Companion object of Cat class
  * Define the event receivable by Cat actor
  */
object Cat {
  private case object TickKey
  private case object ChangeMood
}

/** A simulated cat with a mood
  *
  * @constructor create a new cat with an ID and a randomly selected mood
  * @param catID : the cat ID
  * @attribute mood : the cat mood (see trait Mood)
  */
class Cat(catID : Int, saverActor : ActorRef) extends Actor with Timers {
  private val id : String = catID.toString
  private val saver : ActorRef = saverActor
  private var mood : Mood = randMood()

  /** Randomly choose a mood among the legal ones
    * @return randomly chosen mood
    */
  private def randMood(): Mood = {
    Random.nextInt(7) match {
      case 0 => GROWL
      case 1 => HISS
      case 2 => PURR
      case 3 => THROWGLASS
      case 4 => ROLLONFLOOR
      case 5 => SCRATCHCHAIRS
      case 6 => LOOKDEEPINEYES
    }
  }

  /** Cat life definition
    * 1. New random mood every 27 seconds
    * 2. Send message SaveMood to saver
    * */
  import Cat._
  // Each 27 second self-send ChangeMood message
  timers.startPeriodicTimer(TickKey, ChangeMood, 27.second)

  def receive = {
    // When receiving ChangeMood, randomly select a new mood
    case ChangeMood => {
      val prevMood = mood.sound
      mood = randMood()
      saver ! Saver.SaveMood(id, System.currentTimeMillis().toString, mood.sound, prevMood)
    }
  }
  /** ----------------------------------- */
}
