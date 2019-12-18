package org.joaogsma.actors.metrics
import akka.actor.typed.ActorRef
import akka.actor.typed.Behavior
import akka.actor.typed.scaladsl.ActorContext
import akka.actor.typed.scaladsl.Behaviors
import org.joaogsma.actors.metrics.MetricActor.Close
import org.joaogsma.actors.metrics.MetricActor.MetricMessage
import org.joaogsma.actors.metrics.MetricActor.Occurred
import org.joaogsma.models.Mood.Mood

import scala.collection.mutable

class HistogramActor(
    context: ActorContext[MetricMessage],
    notifyOnClose: ActorRef[ActorRef[MetricMessage]])
    extends MetricActor(context, notifyOnClose) {

  private val histogram: mutable.Map[Mood, Int] = mutable.Map.empty

  override def onMessage(msg: MetricMessage): Behavior[MetricMessage] = msg match {
    case Occurred(moods) =>
      moods.foreach(mood => histogram.put(mood, histogram.getOrElseUpdate(mood, 0) + 1))
      this
    case Close() => close()
  }

  override def logMetric(): Unit = {
    val str = new StringBuilder()
        .append("Histogram metric:\n")
        .append(
          histogram
              .toSeq
              .sortBy(_._1.id)
              .map { case (mood, count) => "   %s: %d".format(mood, count)}
              .mkString("\n"))
        .toString()
    context.log.info(str)
  }
}

object HistogramActor {
  def apply(notifyOnClose: ActorRef[ActorRef[MetricMessage]]): Behavior[MetricMessage] =
      Behaviors.setup(new HistogramActor(_, notifyOnClose))
}
