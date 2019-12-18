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

  def apply(): Behavior[ActorRef[MetricActor.MetricMessage]] = Behaviors.setup { context =>
    val metricActors: Set[ActorRef[MetricActor.MetricMessage]] =
        Set(context.spawn(HistogramActor(context.self), "HistogramMetric"))

    val moodHistoryActor: ActorRef[MoodHistoryActor.Message] =
        context.spawn(MoodHistoryActor(metricActors), "MoodHistory")

    val random: Random = new Random()
    val catActors: Seq[ActorRef[CatActor.Message]] = (0 until NUMBER_OF_CATS)
        .map(id => context.spawnAnonymous(CatActor(id, moodHistoryActor, random)))

    context.spawn[Nothing](ClockActor(catActors, MOOD_CHANGES_PER_CAT), "ClockActor")

    waitForMetrics(metricActors)
  }

  def waitForMetrics(
      remaining: Set[ActorRef[MetricActor.MetricMessage]])
    : Behavior[ActorRef[MetricActor.MetricMessage]] = {
    if (remaining.isEmpty) {
      return Behaviors.stopped
    }
    Behaviors.receiveMessage(metricActor => waitForMetrics(remaining - metricActor))
  }

  def main(args: Array[String]): Unit = {
    ActorSystem(apply(), "App")
  }
}
