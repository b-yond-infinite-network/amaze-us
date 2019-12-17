package org.joaogsma.actors.metrics

import akka.actor.typed.scaladsl.AbstractBehavior
import akka.actor.typed.scaladsl.ActorContext
import org.joaogsma.actors.metrics.MetricActor.MetricMessage
import org.joaogsma.models.Mood.Mood

abstract class MetricActor(
    context: ActorContext[MetricMessage])
    extends AbstractBehavior[MetricMessage](context)

object MetricActor {
  trait MetricMessage
  final case class Occurred(moods: Iterator[Mood]) extends MetricMessage
  final case class Close() extends MetricMessage
}
