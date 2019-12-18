package org.joaogsma.actors.metrics

import akka.actor.typed.ActorRef
import akka.actor.typed.Behavior
import akka.actor.typed.scaladsl.AbstractBehavior
import akka.actor.typed.scaladsl.ActorContext
import akka.actor.typed.scaladsl.Behaviors
import org.joaogsma.actors.metrics.MetricActor.MetricMessage
import org.joaogsma.models.Mood.Mood

abstract class MetricActor(
    context: ActorContext[MetricMessage],
    private val notifyOnClose: ActorRef[ActorRef[MetricMessage]])
    extends AbstractBehavior[MetricMessage](context) {

  protected def logMetric(): Unit

  protected def close(): Behavior[MetricMessage] = {
    logMetric()
    notifyOnClose ! context.self
    Behaviors.stopped
  }
}

object MetricActor {
  trait MetricMessage
  final case class Occurred(moods: Iterator[Mood]) extends MetricMessage
  final case class Close() extends MetricMessage
}
