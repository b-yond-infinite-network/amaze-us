package actors.ingress

import java.time.{Duration, Instant}

import actors.ingress.MoodPublisher.PublishedMood
import actors.ingress.OverlordActor.Mood
import akka.actor.{Actor, ActorLogging, ActorRef, Timers}
import com.google.inject.assistedinject.Assisted
import javax.inject.{Inject, Named}

import scala.util.Random


object MoodyCatActor {
  object ChangeMoodKey
  object ChangeMood

  trait Factory {
    def apply(possibleMoods: Seq[OverlordActor.Mood], moodChangeInterval: Duration): Actor
  }
}

/**
  * This actor represents a cat in the world, emitting a random mood
  * @param possibleMoods - A list of possible moods a cat would go through
  * @param moodChangeFrequency - How often does the cat change mood
  * @param moodPublishActor - An actor to receive published mood
  */
class MoodyCatActor @Inject() (@Assisted possibleMoods: Seq[OverlordActor.Mood],
                               @Assisted moodChangeFrequency: Duration,
                               @Named("mood-publish-router") moodPublishActor: ActorRef)
extends Actor with Timers with ActorLogging {
  import MoodyCatActor._

  val random = new Random

  override def preStart(): Unit = {
    setupMoodSwings()
  }

  override def receive: Receive = {
    case ChangeMood =>
      val myMood = currentMood
      val publishedMood =
        PublishedMood(
          catName = self.path.name,
          emotionName = myMood.name,
          emotion = myMood.emotion,
          timestamp = Instant.now.getEpochSecond
        )
      moodPublishActor ! publishedMood
  }

  def currentMood: Mood = {
    possibleMoods(random.nextInt(possibleMoods.length))
  }

  def setupMoodSwings(): Unit = {
    //setup a periodic timer which would cause the cat to switch mood.
    timers.startPeriodicTimer(ChangeMoodKey, ChangeMood, moodChangeFrequency)
  }
}
