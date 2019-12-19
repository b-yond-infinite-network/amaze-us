package org.joaogsma.actors

import akka.actor.typed.ActorRef
import akka.actor.typed.Behavior
import akka.actor.typed.scaladsl.AbstractBehavior
import akka.actor.typed.scaladsl.ActorContext
import akka.actor.typed.scaladsl.Behaviors
import org.joaogsma.actors.metrics.MetricActor
import org.joaogsma.models.Mood._

import scala.collection.mutable

/** Actor responsible for aggregating the mood history of all cat instances. Whenever a cat's mood
  * simulation is complete, it is sent to the metric actors.
  *
  * @param context       actor context instance.
  * @param metricActors  metric actors to notify when a cat's mood history is complete.
  */
class MoodHistoryActor(
    context: ActorContext[MoodHistoryActor.Message],
    private val metricActors: Iterable[ActorRef[MetricActor.Message]])
    extends AbstractBehavior[MoodHistoryActor.Message](context) {

  /** Mutable map containing the mood history of each cat */
  private val moodHistory: mutable.Map[Int, mutable.ArrayBuffer[Mood]] = mutable.Map.empty

  /** Handles messages received by this actor. On a `MoodHistoryActor.MoodChange` message, the
    * `moodHistory` is updated. On a `MoodHistoryActor.Close` message, the cat's mood history is sent
    * to all the metric actors and, if it is the last active cat, this actor also sends a
    * `MetricActor.Close` message to all metric actors and then stops.
    *
    * @param msg  the message received by this actor.
    */
  override def onMessage(msg: MoodHistoryActor.Message): Behavior[MoodHistoryActor.Message] = {
    msg match {
      case MoodHistoryActor.MoodChange(catId, currentMood) =>
        moodHistory
            .getOrElseUpdate(catId, mutable.ArrayBuffer.empty)
            .append(currentMood)
      case MoodHistoryActor.Close(catId: Int) =>
        val moods: Seq[Mood] = moodHistory
            .remove(catId)
            .map(_.toSeq)
            .getOrElse(throw new IllegalArgumentException("Unknown cat index %d".format(catId)))
        metricActors.foreach(_ ! MetricActor.Occurred(moods.iterator))
        if (moodHistory.isEmpty) {
          metricActors.iterator.foreach(_ ! MetricActor.Close)
          return Behaviors.stopped
        }
    }
    this
  }
}

/** Companion object defining the messages accepted by `MoodHistoryActor` instances, as well as a
  * factory method for them.
  */
object MoodHistoryActor {
  trait Message
  final case class MoodChange(catId: Int, currentMood: Mood) extends Message
  final case class Close(catId: Int) extends Message

  /** Factory method for `MoodHistoryActor` instances */
  def apply(metricActors: Iterable[ActorRef[MetricActor.Message]]): Behavior[Message] =
      Behaviors.setup(context => new MoodHistoryActor(context, metricActors))
}
