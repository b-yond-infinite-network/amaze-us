package org.joaogsma.actors.metrics

import akka.actor.typed.ActorRef
import akka.actor.typed.Behavior
import akka.actor.typed.scaladsl.AbstractBehavior
import akka.actor.typed.scaladsl.ActorContext
import akka.actor.typed.scaladsl.Behaviors
import org.joaogsma.actors.metrics.MetricActor.Message
import org.joaogsma.models.Mood.Mood

/** Trait representing actors which compute some metric on the cats' mood histories.
  *
  * @param context        actor context instance.
  * @param notifyOnClose  actor to be notified when this actor finished its execution.
  */
abstract class MetricActor(
    context: ActorContext[Message],
    private val notifyOnClose: ActorRef[ActorRef[Message]])
    extends AbstractBehavior[Message](context) {

  /** Abstract method representing the logging of the final metric value or values */
  protected def logMetric(): Unit

  /** Stops the actor, logging the metric and notifying `notifyOnClose` first */
  protected def close(): Behavior[Message] = {
    logMetric()
    notifyOnClose ! context.self
    Behaviors.stopped
  }
}

/** Companion object defining the messages accepted by `MetricActor` instances */
object MetricActor {
  trait Message
  final case class Occurred(moods: Iterable[Mood]) extends Message
  case object Close extends Message
}
