package actors.ingress

import java.time.{Duration, Instant}

import actors.ingress.MoodPublisher.PublishedMood
import actors.ingress.MoodyCatActor.ChangeMood
import actors.ingress.OverlordActor.Mood
import akka.actor.{ActorSystem, Props}
import akka.testkit.{ImplicitSender, TestKit, TestProbe}
import org.scalatest.{BeforeAndAfterAll, Matchers, WordSpecLike}

class MoodyCatActorSpec extends TestKit(ActorSystem("MoodyCatActorSpec"))
  with ImplicitSender
  with WordSpecLike
  with Matchers
  with BeforeAndAfterAll {

  override def afterAll: Unit = {
    TestKit.shutdownActorSystem(system)
  }

  "Moody cat actor" must {
    "setup a timer to change mood" in {

      val probe = TestProbe()
      val moods =
        Seq[OverlordActor.Mood](
          Mood("some mood", "some emotion"),
          Mood("some other mood", "some other emotion")
        )
      val myMood: Mood = moods.head
      val actor = system.actorOf(Props(new MoodyCatActor(moods, Duration.ofMillis(100), probe.ref) {
        override def currentMood: Mood = myMood
        val moodToPublish = PublishedMood(self.path.name, myMood.name, myMood.emotion, Instant.now.getEpochSecond)
        override def setupMoodSwings(): Unit =
          probe.ref ! moodToPublish
      }))

      actor ! ChangeMood
      probe.expectMsg(PublishedMood(actor.path.name, myMood.name, myMood.emotion, Instant.now.getEpochSecond))
    }
  }
}
