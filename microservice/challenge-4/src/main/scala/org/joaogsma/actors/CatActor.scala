package org.joaogsma.actors

import akka.actor.typed.ActorRef
import akka.actor.typed.Behavior
import akka.actor.typed.scaladsl.AbstractBehavior
import akka.actor.typed.scaladsl.ActorContext
import akka.actor.typed.scaladsl.Behaviors
import org.joaogsma.models.Mood
import org.joaogsma.models.Mood._

import scala.util.Random

class CatActor(
    context: ActorContext[CatActor.Message],
    val id: Int,
    private val moodHistoryActor: ActorRef[MoodHistoryActor.Message],
    private val random: Random)
    extends AbstractBehavior[CatActor.Message](context) {

  private var currentMood: Mood = Mood.MIAW

  override def onMessage(msg: CatActor.Message): Behavior[CatActor.Message] = msg match {
    case CatActor.ChangeMood() =>
      val previousMood = currentMood
      currentMood = if (currentMood == Mood.MIAW) randomMood else Mood.MIAW
      moodHistoryActor ! MoodHistoryActor.MoodChange(id, currentMood)
      this
    case CatActor.Close() =>
      moodHistoryActor ! MoodHistoryActor.Close(id)
      Behaviors.stopped
  }

  private def randomMood: Mood = Mood(random.nextInt(Mood.maxId))
}

object CatActor {
  trait Message
  final case class ChangeMood() extends Message
  final case class Close() extends Message

  def apply(
      id: Int,
      moodHistoryActor: ActorRef[MoodHistoryActor.Message],
      random: Random): Behavior[Message] = {
    Behaviors.setup(new CatActor(_, id, moodHistoryActor, random))
  }
}
