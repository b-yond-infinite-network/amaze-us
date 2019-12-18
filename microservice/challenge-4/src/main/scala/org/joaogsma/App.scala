package org.joaogsma

import akka.actor.typed.ActorRef
import akka.actor.typed.ActorSystem
import akka.actor.typed.Behavior
import akka.actor.typed.scaladsl.Behaviors
import org.joaogsma.actors.CatActor
import org.joaogsma.actors.ClockActor
import org.joaogsma.actors.MoodHistoryActor
import org.joaogsma.actors.metrics.HistogramActor
import org.joaogsma.actors.metrics.MetricActor

import scala.util.Random

object App {
  private val NUMBER_OF_CATS: Int = 5
  private val MOOD_CHANGES_PER_CAT: Int = 3

  final case class Start()
  final case class Finished()

  def apply(): Behavior[Nothing] = Behaviors.setup[Nothing] { context =>
    val metricActors: Seq[ActorRef[MetricActor.MetricMessage]] =
      Seq(context.spawn(HistogramActor(), "Histogram"))

    val moodHistoryActor: ActorRef[MoodHistoryActor.Message] =
        context.spawn(MoodHistoryActor(metricActors), "MoodHistory")

    val random: Random = new Random()
    val catActors: Seq[ActorRef[CatActor.Message]] = (0 until NUMBER_OF_CATS)
        .map(id => context.spawnAnonymous(CatActor(id, moodHistoryActor, random)))

    context.spawn[Nothing](ClockActor(catActors, MOOD_CHANGES_PER_CAT), "ClockActor")

    Behaviors.unhandled
  }

  def main(args: Array[String]): Unit = {
    ActorSystem[Nothing](apply(), "App")
  }
}
