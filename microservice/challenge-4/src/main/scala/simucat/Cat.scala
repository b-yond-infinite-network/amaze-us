package simucat

import scala.util.Random

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

class Cat(catID : Int) {
  private val id : Int = catID
  private var mood : Mood = chooseMood()
  private var alive : Boolean = true

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

  def live(): Unit = {
    while (alive) {
      mood = chooseMood()
      println(s"Cat $id : ${mood.sound}")
      Thread.sleep(27000)
    }
  }

  def kill(): Unit = {
    alive = false
  }
}
