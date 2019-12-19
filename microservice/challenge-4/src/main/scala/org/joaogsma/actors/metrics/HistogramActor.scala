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

/** Actor responsible for computing a histogram of cat moods from all the simulated cats.
  *
  * @param context        actor context instance.
  * @param notifyOnClose  actor to be notified when this actor finished its execution.
  */
class HistogramActor(
    context: ActorContext[MetricMessage],
    notifyOnClose: ActorRef[ActorRef[MetricMessage]])
    extends MetricActor(context, notifyOnClose) {

  /** Mutable map containing the mood histogram */
  private val histogram: mutable.Map[Mood, Int] = mutable.Map.empty

  /** Handles messages received by this actor. On a `MetricActor.Occurred` message, the histogram is
    * updated. On a `MetricActor.Close` message, this actor closes.
    *
    * @param msg  the message received by this actor.
    */
  override def onMessage(msg: MetricMessage): Behavior[MetricMessage] = msg match {
    case Occurred(moods) =>
      moods.foreach(mood => histogram.put(mood, histogram.getOrElseUpdate(mood, 0) + 1))
      this
    case Close => close()
  }

  /** Logs the metric through `ActorContext[MetricActor.MetricMessage].log` */
  override def logMetric(): Unit = {
    val str = new StringBuilder()
        .append("Histogram metric:\n")
        .append(
          histogram
              .toSeq
              .sortBy(_._2 * -1)
              .map { case (mood, count) => "   %s: %d".format(mood, count)}
              .mkString("\n"))
        .toString()
    context.log.info(str)
  }
}

/** Companion object defining a factory method for `HistogramActor` */
object HistogramActor {
  /** Factory method for `HistogramActor` instances */
  def apply(notifyOnClose: ActorRef[ActorRef[MetricMessage]]): Behavior[MetricMessage] =
      Behaviors.setup(new HistogramActor(_, notifyOnClose))
}
