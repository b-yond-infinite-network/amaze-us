package org.joaogsma.actors.metrics

import akka.actor.testkit.typed.scaladsl.BehaviorTestKit
import akka.actor.typed.ActorRef
import akka.actor.typed.scaladsl.ActorContext
import akka.actor.typed.scaladsl.Behaviors
import org.assertj.core.api.Assertions.assertThat
import org.joaogsma.models.Mood
import org.joaogsma.models.Mood.Mood
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class HistogramActorTests {
  private val NOTIFY_ON_CLOSE_ACTOR: BehaviorTestKit[ActorRef[MetricActor.Message]] =
    BehaviorTestKit(Behaviors.empty)

  private final class TestableHistogramActor(
      initialHistogram: Map[Mood, Int],
      context: ActorContext[MetricActor.Message],
      notifyOnClose: ActorRef[ActorRef[MetricActor.Message]])
      extends HistogramActor(context, notifyOnClose) {
    histogram.addAll(initialHistogram)

    def frequency(mood: Mood): Option[Int] = histogram.get(mood)
  }

  @BeforeEach
  def setUp(): Unit = NOTIFY_ON_CLOSE_ACTOR.selfInbox().receiveAll()

  @Nested
  class OccurredMessageTests {
    @Test
    def shouldCreateEntryInMapIfNecessary(): Unit = {
      val histogramActor: BehaviorTestKit[MetricActor.Message] = createHistogramActor()
      val frequency: Mood => Int =
          histogramActor.currentBehavior.asInstanceOf[TestableHistogramActor].frequency(_).get

      histogramActor.run(MetricActor.Occurred(Seq(Mood.MIAW, Mood.ROLLONFLOOR)))

      assertThat(frequency(Mood.MIAW)).isEqualTo(1)
      assertThat(frequency(Mood.ROLLONFLOOR)).isEqualTo(1)
      assertThat(NOTIFY_ON_CLOSE_ACTOR.selfInbox().hasMessages).isFalse
    }

    @Test
    def shouldUpdateMap(): Unit = {
      val histogramActor: BehaviorTestKit[MetricActor.Message] =
          createHistogramActor(Map(Mood.MIAW -> 2, Mood.SCRATCHCHAIRS -> 1))
      val frequency: Mood => Int =
        histogramActor.currentBehavior.asInstanceOf[TestableHistogramActor].frequency(_).get

      histogramActor.run(MetricActor.Occurred(Seq(Mood.MIAW, Mood.SCRATCHCHAIRS)))

      assertThat(frequency(Mood.MIAW)).isEqualTo(3)
      assertThat(frequency(Mood.SCRATCHCHAIRS)).isEqualTo(2)
      assertThat(NOTIFY_ON_CLOSE_ACTOR.selfInbox().hasMessages).isFalse
    }
  }

  @Nested
  class CloseMessageTests {
    @Test
    def shouldSendMessageAndStop(): Unit = {
      val histogramActor: BehaviorTestKit[MetricActor.Message] = createHistogramActor()

      histogramActor.run(MetricActor.Close)

      assertThat(histogramActor.isAlive).isFalse
      NOTIFY_ON_CLOSE_ACTOR.selfInbox().expectMessage(histogramActor.ref)
      assertThat(NOTIFY_ON_CLOSE_ACTOR.selfInbox().hasMessages).isFalse
    }
  }

  private def createHistogramActor(
      initialHistogram: Map[Mood, Int] = Map.empty): BehaviorTestKit[MetricActor.Message] = {
    BehaviorTestKit(
      Behaviors.setup(new TestableHistogramActor(initialHistogram, _, NOTIFY_ON_CLOSE_ACTOR.ref)))
  }
}
