package com.byond

import scala.concurrent.duration._
import akka.actor.typed.ActorRef
import akka.actor.typed.Behavior
import akka.actor.typed.scaladsl.Behaviors
import scala.util.Random
import java.util.Date
import slick.jdbc.H2Profile.api._

// SQL schema
class Cats(tag: Tag) extends Table[(Int, String, Long)](tag, "CATS") {
  def id = column[Int]("CAT_ID")
  def mood = column[String]("CAT_MOOD")      // Could use a shorter encoding
  def timestamp = column[Long]("TIMESTAMP")
  def * = (id, mood, timestamp)
}

// Cat Actor
object Cat {
  def apply(state: CatState, parent: ActorRef[Response], interval: FiniteDuration): Behavior[Command] = {
    Behaviors.setup { context =>
        Behaviors.withTimers { timers => 
            timers.startTimerWithFixedDelay(Act, interval)
            val cat = new Cat()
            val r = scala.util.Random
            cat.run(parent, state, r)
        }
    }
  }

  def moodFromString(s: String): Option[CatMood] = {
    s match {
      case "miaw" => Some(CatMiaw)
      case "grr" => Some(CatGrowl)
      case "kssss" => Some(CatHiss)
      case "rrrrr" => Some(CatPurr)
      case "cling bling" => Some(CatThrowGlass)
      case "fffff" => Some(CatRollOnFloor)
      case "gratgrat" => Some(CatScratchChairs)
      case "-o-o-___--" => Some(CatLookDeepInEyes)
      case _ => None
    }
  }

  def moodToString(mood: CatMood): String = {
    mood match {
      case CatMiaw           => "miaw"
      case CatGrowl          => "grr"
      case CatHiss           => "kssss"
      case CatPurr           => "rrrrr"
      case CatThrowGlass     => "cling bling"
      case CatRollOnFloor    => "fffff"
      case CatScratchChairs  => "gratgrat"
      case CatLookDeepInEyes => "-o-o-___--"
    }
  }
}
class Cat {
  private def run(parent: ActorRef[Response], state: CatState, r: Random): Behavior[Command] = {
    var currentMood = state.catMood
    Behaviors.receiveMessage { message =>
          message match {
              case Act => {
                  val newMood: CatMood = {
                    // A more realistic model would account for a daily meal
                    val n = r.nextInt(100)
                    currentMood match {
                      case CatMiaw if n < 80 => CatMiaw
                      case CatMiaw if n < 85 => CatLookDeepInEyes
                      case CatMiaw if n < 90 => CatGrowl
                      case CatMiaw if n < 95 => CatHiss
                      case CatMiaw => CatThrowGlass
                      case CatLookDeepInEyes => CatRollOnFloor
                      case CatRollOnFloor => CatPurr
                      case CatPurr => CatMiaw
                      case CatGrowl => CatMiaw
                      case CatHiss => CatMiaw
                      case CatThrowGlass => CatMiaw
                    }
                  }
                  currentMood = newMood
                  val currentState = state.copy(catMood = currentMood, timestamp = (new Date().getTime()) / 1000)
                  parent ! ResponseAddCatState(currentState)
                  Behaviors.same
              }
          }
        }
  }

}