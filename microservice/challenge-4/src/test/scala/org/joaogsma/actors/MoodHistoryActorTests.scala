package org.joaogsma.actors

import akka.actor.testkit.typed.scaladsl.BehaviorTestKit
import akka.actor.typed.ActorRef
import akka.actor.typed.scaladsl.ActorContext
import akka.actor.typed.scaladsl.Behaviors
import org.assertj.core.api.Assertions.assertThat
import org.joaogsma.actors.metrics.MetricActor
import org.joaogsma.models.Mood
import org.joaogsma.models.Mood.Mood
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

import scala.collection.mutable

class MoodHistoryActorTests {
  private val METRIC_ACTORS: Seq[BehaviorTestKit[MetricActor.Message]] =
      Seq.fill(2)(BehaviorTestKit(Behaviors.empty))

  private final class TestableMoodHistoryActor(
      initialMoodHistory: Map[Int, Seq[Mood]],
      context: ActorContext[MoodHistoryActor.Message],
      metricActors: Iterable[ActorRef[MetricActor.Message]])
      extends MoodHistoryActor(context, metricActors) {
    moodHistory.addAll(initialMoodHistory.view.mapValues(mutable.ArrayBuffer.from))

    def history(catId: Int): Option[Seq[Mood]] = moodHistory.get(catId).map(_.toSeq)
  }

  @BeforeEach
  def setUp(): Unit = METRIC_ACTORS.foreach(_.selfInbox().receiveAll())

  @Nested
  class MoodChangeMessageTests {
    @Test
    def shouldCreateEntryInMapIfNecessary(): Unit = {
      val moodHistoryActor: BehaviorTestKit[MoodHistoryActor.Message] = createMoodHistoryActor()
      moodHistoryActor.run(MoodHistoryActor.MoodChange(1, Mood.PURR))
      val history: Seq[Mood] = moodHistoryActor
          .currentBehavior
          .asInstanceOf[TestableMoodHistoryActor]
          .history(1)
          .get
      assertThat(history).isEqualTo(Seq(Mood.PURR))
    }

    @Test
    def shouldUpdateMap(): Unit = {
      val moodHistoryActor: BehaviorTestKit[MoodHistoryActor.Message] =
          createMoodHistoryActor(Map(0 -> Seq(Mood.MIAW, Mood.GROWL)))
      moodHistoryActor.run(MoodHistoryActor.MoodChange(0, Mood.GROWL))
      val history: Seq[Mood] = moodHistoryActor
          .currentBehavior
          .asInstanceOf[TestableMoodHistoryActor]
          .history(0)
          .get
      assertThat(history).isEqualTo(Seq(Mood.MIAW, Mood.GROWL, Mood.GROWL))
    }
  }

  @Nested
  class CloseMessageTests {
    @Test
    def shouldNotifyMetricActorsAndRemoveFromMap(): Unit = {
      val moodHistoryActor: BehaviorTestKit[MoodHistoryActor.Message] =
          createMoodHistoryActor(Map(0 -> Seq(Mood.MIAW), 1 -> Seq(Mood.MIAW, Mood.PURR)))
      moodHistoryActor.run(MoodHistoryActor.Close(1))

      METRIC_ACTORS.foreach { metricActor =>
        metricActor.selfInbox().expectMessage(MetricActor.Occurred(Seq(Mood.MIAW, Mood.PURR)))
        assertThat(metricActor.selfInbox().hasMessages).isFalse
      }

      val history: Option[Seq[Mood]] =
          moodHistoryActor.currentBehavior.asInstanceOf[TestableMoodHistoryActor].history(1)

      assertThat(history.isEmpty).isTrue
    }

    @Test
    def shouldStopActorAfterLastCloseMessage(): Unit = {
      val moodHistoryActor: BehaviorTestKit[MoodHistoryActor.Message] =
          createMoodHistoryActor(Map(0 -> Seq(Mood.MIAW, Mood.THROWGLASS)))
      moodHistoryActor.run(MoodHistoryActor.Close(0))
      assertThat(moodHistoryActor.isAlive).isFalse
    }
  }

  private def createMoodHistoryActor(
      moodHistory: Map[Int, Seq[Mood]] = Map.empty): BehaviorTestKit[MoodHistoryActor.Message] = {
    BehaviorTestKit(
      Behaviors.setup(new TestableMoodHistoryActor(moodHistory, _, METRIC_ACTORS.map(_.ref))))
  }
}
