package example

import akka.NotUsed
import akka.actor.Cancellable

import scala.util.Random
import akka.stream.scaladsl._

import scala.concurrent.duration._

object Cats {
  final case class MoodSwing(id: Int, mood: Mood.Value, ts: Long)

  def change(every: FiniteDuration) = Source
    .tick(0.seconds, every, 0)

  def moods(n: Int)(implicit rnd: Random): Source[MoodSwing, NotUsed] = Source(1 to n)
    .map(i => MoodSwing(i, Mood.random, System.currentTimeMillis()))

  def moodSwings(n: Int, every: FiniteDuration = 27.seconds)(implicit rnd: Random): Source[MoodSwing, Cancellable] =
    change(every)
    .flatMapConcat(_ => moods(n))

  object Mood extends Enumeration {
    type Mood = String
    val DEFAULT        = Value("Miaw")
    val GROWL          = Value("grr")
    val HISS           = Value("kssss")
    val PURR           = Value("rrrrr")
    val THROWGLASS     = Value("cling bling")
    val ROLLONFLOOR    = Value("fffff")
    val SCRATCHCHAIRS  = Value("gratgrat")
    val LOOKDEEPINEYES = Value("-o-o-___--")

    def random(implicit rnd: Random): Mood.Value = Mood.values.toVector(rnd.nextInt(Mood.values.size))
  }
}
