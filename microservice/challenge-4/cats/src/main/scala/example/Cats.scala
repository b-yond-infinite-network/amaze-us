package example

import scala.util.Random

object Cats {
  type CatID = Int // Int as identifier for cats. Not used in this example but could be used to filter out anomalies, etc.
  val NB_OF_CATS: Int = 1000
  val MOOD_DURATION: Int = 27000 // Duration in ms.
  val MOOD_GENERATOR: MoodGenerator = new RandomMoodGenerator()

  /* The assumption here is that all 1000 cats' mood swings are synchronized. */
  def synchronizedMoodSwings(n: Int): Iterable[(CatID, Mood.Value)] = (1 to n) map changeMood

  def changeMood(catId: CatID): (CatID, Mood.Value) = (catId, MOOD_GENERATOR.nextMood)
}

object Mood extends Enumeration {
  type Mood = Value
  val DEFAULT: Mood = Value("Miaw")
  val GROWL: Mood = Value("grr")
  val HISS: Mood = Value("kssss")
  val PURR: Mood = Value("rrrrr")
  val THROWGLASS: Mood = Value("cling bling")
  val ROLLONFLOOR: Mood = Value("fffff")
  val SCRATCHCHAIRS: Mood = Value("gratgrat")
  val LOOKDEEPINEYES: Mood = Value("-o-o-___--")
}

trait MoodGenerator {
  def nextMood: Mood.Value
}

class RandomMoodGenerator() extends MoodGenerator {
  final private val randomizer: Random = new Random()

  override def nextMood: Mood.Value = Mood.values.toIndexedSeq(randomizer.nextInt(Mood.values.size))
}