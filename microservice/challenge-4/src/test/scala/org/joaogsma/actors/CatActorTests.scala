package org.joaogsma.actors

import akka.actor.testkit.typed.scaladsl.BehaviorTestKit
import akka.actor.typed.ActorRef
import akka.actor.typed.Behavior
import akka.actor.typed.scaladsl.ActorContext
import akka.actor.typed.scaladsl.Behaviors
import org.assertj.core.api.Assertions.assertThat
import org.joaogsma.actors.CatActor.Message
import org.joaogsma.models.Mood
import org.joaogsma.models.Mood.Mood
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.Mockito.mock
import org.mockito.Mockito.never
import org.mockito.Mockito.verify
import org.mockito.Mockito.when

import scala.util.Random

class CatActorTests {
  private val RANDOM: Random = mock(classOf[Random])
  private val MOOD_HISTORY_ACTOR: BehaviorTestKit[MoodHistoryActor.Message] =
      BehaviorTestKit(Behaviors.empty)

  final class TestableCatActor(
      mood: Mood,
      context: ActorContext[Message],
      id: Int,
      moodHistoryActor: ActorRef[MoodHistoryActor.Message],
      random: Random) extends CatActor(context, id, moodHistoryActor, random) {
    currentMood = mood
  }

  @BeforeEach
  def setUp(): Unit = {
    MOOD_HISTORY_ACTOR.selfInbox().receiveAll()
  }

  @Nested
  class CloseMessageTests {
    @Test
    def whenACloseMessageIsReceived_shouldNotifyMoodHistoryActorAndStop(): Unit = {
      val catActor = createCatActor()
      catActor.run(CatActor.Close)

      val expectedMessages: Seq[MoodHistoryActor.Message] =
          Seq(MoodHistoryActor.MoodChange(0, Mood.MIAW), MoodHistoryActor.Close(0))

      assertThat(MOOD_HISTORY_ACTOR.selfInbox().receiveAll).isEqualTo(expectedMessages)
      verify(RANDOM, never).nextDouble()
      verify(RANDOM, never).nextInt(anyInt())
    }
  }

  @Nested
  class ChangeMoodMessageTests {
    @Test
    def shouldKeepTheMoodCorrectly(): Unit = {
      when(RANDOM.nextDouble()).thenReturn(0.49)

      val catActor = createCatActor(Mood.PURR)
      catActor.run(CatActor.ChangeMood)

      val expectedMessages: Seq[MoodHistoryActor.Message] =
          Seq(MoodHistoryActor.MoodChange(0, Mood.MIAW), MoodHistoryActor.MoodChange(0, Mood.PURR))

      assertThat(MOOD_HISTORY_ACTOR.selfInbox().receiveAll).isEqualTo(expectedMessages)
      verify(RANDOM, never).nextInt(anyInt())
      assertThat(catActor.hasEffects()).isFalse
    }

    @Test
    def shouldChangeFromDefaultMoodCorrectly(): Unit = {
      when(RANDOM.nextDouble()).thenReturn(0.51)
      when(RANDOM.nextInt(anyInt())).thenReturn(3)  // Equivalent to THROWGLASS

      val catActor = createCatActor()
      catActor.run(CatActor.ChangeMood)

      val expectedMessages: Seq[MoodHistoryActor.Message] = Seq(
        MoodHistoryActor.MoodChange(0, Mood.MIAW),
        MoodHistoryActor.MoodChange(0, Mood.THROWGLASS))

      assertThat(MOOD_HISTORY_ACTOR.selfInbox().receiveAll).isEqualTo(expectedMessages)
      assertThat(catActor.hasEffects()).isFalse
    }

    @Test
    def shouldChangeFromNonDefaultMoodCorrectly(): Unit = {
      when(RANDOM.nextDouble()).thenReturn(0.51)

      val catActor = createCatActor(Mood.HISS)
      catActor.run(CatActor.ChangeMood)

      val expectedMessages: Seq[MoodHistoryActor.Message] =
          Seq(MoodHistoryActor.MoodChange(0, Mood.MIAW), MoodHistoryActor.MoodChange(0, Mood.MIAW))

      assertThat(MOOD_HISTORY_ACTOR.selfInbox().receiveAll).isEqualTo(expectedMessages)
      verify(RANDOM, never).nextInt(anyInt())
      assertThat(catActor.hasEffects()).isFalse
    }
  }

  private def createCatActor(mood: Mood = Mood.MIAW): BehaviorTestKit[CatActor.Message] = {
    val behavior: Behavior[CatActor.Message] =
        Behaviors.setup(new TestableCatActor(mood, _, 0, MOOD_HISTORY_ACTOR.ref, RANDOM))
    BehaviorTestKit(behavior)
  }
}
