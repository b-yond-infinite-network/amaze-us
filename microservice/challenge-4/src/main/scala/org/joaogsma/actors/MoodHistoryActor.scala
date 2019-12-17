package org.joaogsma.actors

import akka.actor.typed.ActorRef
import akka.actor.typed.Behavior
import akka.actor.typed.scaladsl.AbstractBehavior
import akka.actor.typed.scaladsl.ActorContext
import akka.actor.typed.scaladsl.Behaviors
import org.joaogsma.actors.metrics.MetricActor
import org.joaogsma.models.Mood._

import scala.collection.mutable

class MoodHistoryActor(
    context: ActorContext[MoodHistoryActor.Message],
    private val metricActors: Iterable[ActorRef[MetricActor.MetricMessage]])
    extends AbstractBehavior[MoodHistoryActor.Message](context) {

  private val moodHistory: mutable.Map[Int, mutable.ArrayBuffer[Mood]] = mutable.Map.empty

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
          metricActors.iterator.foreach(_ ! MetricActor.Close())
          return Behaviors.stopped
        }
    }
    this
  }
}

object MoodHistoryActor {
  trait Message
  final case class MoodChange(catId: Int, currentMood: Mood) extends Message
  final case class Close(catId: Int) extends Message

  def apply(metricActors: Iterable[ActorRef[MetricActor.MetricMessage]]): Behavior[Message] =
      Behaviors.setup(context => new MoodHistoryActor(context, metricActors))
}
