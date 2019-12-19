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

/** Entrypoint of the application, and user guardian actor. This actor is responsible for spawning
  * all other actors and then waiting for the completion of all metrics.
  */
object AppMain extends App {
  /** Number of cats to simulate */
  private val NUMBER_OF_CATS: Int = 1000
  /** Number of mood changes per cat in the simulation */
  private val MOOD_CHANGES_PER_CAT: Int = 3200

  ActorSystem(apply(), "App")

  /** Spawns all other actors */
  def apply(): Behavior[ActorRef[MetricActor.Message]] = Behaviors.setup { context =>
    val metricActors: Set[ActorRef[MetricActor.Message]] =
        Set(context.spawn(HistogramActor(context.self), "HistogramMetric"))

    val moodHistoryActor: ActorRef[MoodHistoryActor.Message] =
        context.spawn(MoodHistoryActor(metricActors), "MoodHistory")

    val random: Random = new Random()
    val catActors: Seq[ActorRef[CatActor.Message]] = (0 until NUMBER_OF_CATS)
        .map(id => context.spawnAnonymous(CatActor(id, moodHistoryActor, random)))

    context.spawn[Nothing](ClockActor(catActors, MOOD_CHANGES_PER_CAT), "ClockActor")

    waitForMetrics(metricActors)
  }

  /** Waits for all the metric actors to finish executing */
  def waitForMetrics(
      remaining: Set[ActorRef[MetricActor.Message]]): Behavior[ActorRef[MetricActor.Message]] = {
    if (remaining.isEmpty) {
      return Behaviors.stopped
    }
    Behaviors.receiveMessage(metricActor => waitForMetrics(remaining - metricActor))
  }
}
