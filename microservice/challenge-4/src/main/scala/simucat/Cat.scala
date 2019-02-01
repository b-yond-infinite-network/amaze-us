package simucat

import scala.util.Random
import scala.concurrent.{ExecutionContext, Future}

/** Available moods for the Cat
  *
  * @attribute sound : phonic representation of the mood
  */
sealed trait Mood {
  val sound : String
}
case object GROWL extends Mood {val sound = "grr"}
case object HISS extends Mood {val sound = "kssss"}
case object PURR extends Mood {val sound = "rrrrr"}
case object THROWGLASS extends Mood {val sound = "cling bling"}
case object ROLLONFLOOR extends Mood {val sound = "fffff"}
case object SCRATCHCHAIRS extends Mood {val sound = "gratgrat"}
case object LOOKDEEPINEYES extends Mood {val sound = "-o-o-___--"}

/** A simulated cat with a mood
  *
  * @constructor create a new cat with an ID and a randomly selected mood
  * @param catID : the cat ID
  * @attribute mood : the cat mood (see trait Mood)
  * @attribute alive : Boolean indicating if the Cat is alive or not
  */
class Cat(catID : Int) {
  private val id : Int = catID
  private var mood : Mood = chooseMood()
  private var alive : Boolean = true

  /** Randomly choose a mood among the legal ones
    *
    * @return randomly chosen mood
    */
  def chooseMood(): Mood = {
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

  /** Life cycle of the cat
    * Each 27 seconds, the cat changes its mood.
    * The loop is infinite until the kill() call.
    */
  def live(): Unit = {
    implicit val ec = ExecutionContext.global
    Future {
      while (alive) {
        mood = chooseMood()
        println(s"Cat $id : ${mood.sound}")
        Thread.sleep(27000)
      }
    }(ec)
  }

  /** End the life cycle of the cat
    * Set alive attribute to false to end loop in live()
    */
  def kill(): Unit = {
    alive = false
  }
}
