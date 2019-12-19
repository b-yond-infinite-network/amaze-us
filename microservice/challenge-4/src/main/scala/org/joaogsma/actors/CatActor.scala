package org.joaogsma.actors

import akka.actor.typed.ActorRef
import akka.actor.typed.Behavior
import akka.actor.typed.scaladsl.AbstractBehavior
import akka.actor.typed.scaladsl.ActorContext
import akka.actor.typed.scaladsl.Behaviors
import org.joaogsma.actors.CatActor._
import org.joaogsma.models.Mood
import org.joaogsma.models.Mood._

import scala.util.Random

/** Actor responsible for simulating a single cat.
  *
  * @param context           actor context instance.
  * @param id                cat id.
  * @param moodHistoryActor  actor to inform when this actor's mood changes.
  * @param random            random number generator to use when changing moods.
  */
class CatActor(
    context: ActorContext[Message],
    val id: Int,
    private val moodHistoryActor: ActorRef[MoodHistoryActor.Message],
    private val random: Random)
    extends AbstractBehavior[Message](context) {

  private var currentMood: Mood = Mood.MIAW

  /** Handles messages received by the actor. On a `CatActor.ChangeMood` message, the actor changes
    * the current mood and then updates the `MoodHistoryActor`. On a `CatActor.Closed` message, the actor
    * updates the `MoodHistoryActor` and then stops.
    *
    * @param msg  the message received by this actor.
    */
  override def onMessage(msg: Message): Behavior[Message] = msg match {
    case ChangeMood =>
      currentMood = maybeChangeMood
      moodHistoryActor ! MoodHistoryActor.MoodChange(id, currentMood)
      this
    case Close =>
      moodHistoryActor ! MoodHistoryActor.Close(id)
      Behaviors.stopped
  }

  /** Returns a random `Mood` according to a few rules:
    * 1) There's a 50% chance that the current mood will be kept
    * 2) Non-default moods change to the default mood
    * 3) When changing from the default mood, all other moods have equal probability of being chosen
    */
  private def maybeChangeMood: Mood = {
    if (random.nextDouble() < PROB_KEEPING_MOOD) {
      return currentMood
    }

    if (currentMood != Mood.MIAW) {
      return Mood.MIAW
    }

    val randInt = random.nextInt(Mood.maxId - 1)
    Mood(if (randInt < currentMood.id) randInt else randInt + 1)
  }
}

/** Companion object defining the messages accepted by `CatActor` instances, as well as a factory
  * method for them.
  */
object CatActor {
  trait Message
  case object ChangeMood extends Message
  case object Close extends Message

  private val PROB_KEEPING_MOOD: Double = 0.5

  /** Factory method for `CatActor` instances */
  def apply(
      id: Int,
      moodHistoryActor: ActorRef[MoodHistoryActor.Message],
      random: Random): Behavior[Message] = {
    Behaviors.setup(new CatActor(_, id, moodHistoryActor, random))
  }
}
