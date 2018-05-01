import scala.util.Random
import java.io._

// those cats are weird
class ThoseCats(val catCurrentMood: Int) {

  val GROWL         = "grr"
  val HISS          = "kssss"
  val PURR          = "rrrrr"
  val THROWGLASS    = "cling bling"
  val ROLLONFLOOR   = "fffff"
  val SCRATCHCHAIRS = "gratgrat"
  val LOOKDEEPINEYES = "-o-o-___--"

  val possibleMood  = Array(GROWL, HISS, PURR, THROWGLASS, ROLLONFLOOR, SCRATCHCHAIRS, LOOKDEEPINEYES)

  var mood = catCurrentMood

  println("miaw")

  def randomCatName(length: Int): String = {
    val chars = ('a' to 'z') ++ ('A' to 'Z') ++ ('0' to '9')
    val sb = new StringBuilder
    for (i <- 1 to length) {
      val randomNum = util.Random.nextInt(chars.length)
      sb.append(chars(randomNum))
    }
    sb.toString
  }

  val catName = randomCatName(30)

  def mood {
    if( mood < possibleMood.length())
      mood
  }

  def changeMood {
    val timer = new java.util.Timer()
    val timerTask = new java.util.TimerTask { def run() = { mood = util.Random.nextInt(possibleMood.length()) } }

    timer.schedule(timerTask, 1000L, 1000L)
    timerTasktask.cancel()
  }
}