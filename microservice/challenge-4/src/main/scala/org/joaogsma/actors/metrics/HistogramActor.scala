package org.joaogsma.actors.metrics
import akka.actor.typed.Behavior
import akka.actor.typed.scaladsl.ActorContext
import akka.actor.typed.scaladsl.Behaviors
import org.joaogsma.actors.metrics.MetricActor.Close
import org.joaogsma.actors.metrics.MetricActor.MetricMessage
import org.joaogsma.actors.metrics.MetricActor.Occurred
import org.joaogsma.models.Mood.Mood

import scala.collection.mutable

class HistogramActor(context: ActorContext[MetricMessage]) extends MetricActor(context) {
  private val histogram: mutable.Map[Mood, Int] = mutable.Map.empty

  override def onMessage(msg: MetricMessage): Behavior[MetricMessage] = msg match {
    case Occurred(moods) =>
      moods.foreach(mood => histogram.put(mood, histogram.getOrElseUpdate(mood, 0) + 1))
      this
    case Close() =>
      context.log.info("Histogram metric: {}", histogram)
      Behaviors.stopped
  }
}

object HistogramActor {
  def apply(): Behavior[MetricMessage] = Behaviors.setup(new HistogramActor(_))
}
