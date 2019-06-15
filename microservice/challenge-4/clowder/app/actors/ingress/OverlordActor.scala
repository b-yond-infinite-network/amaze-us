package actors.ingress

import akka.actor.{Actor, ActorLogging, Timers}
import com.typesafe.config.Config
import javax.inject.Inject
import play.api.libs.concurrent.InjectedActorSupport

import scala.collection.JavaConverters._
import scala.concurrent.duration._

object OverlordActor {
  object WakeUpCats
  object WakeUpCatsKey
  case class Mood (name: String, emotion: String)
}

/**
  * An actor responsible for managing cats in the world.
  * The actor is scheduled to wakeup all the cats when the actor starts.
  * @param moodyCatActorFactory - A factory to create cats
  * @param config - type space config
  */
class OverlordActor @Inject() (moodyCatActorFactory: MoodyCatActor.Factory, config: Config)
  extends Actor with InjectedActorSupport with Timers with ActorLogging {

  import OverlordActor._

  override def preStart(): Unit = {
    scheduleCatOverlordship()
  }

  override def receive: Receive = {
    case WakeUpCats =>

      log.info("Waking up the clowder.....")

      val moodChangeInterval = config.getDuration("clowder.moodchange")
      val possibleMoods =
        config.getObjectList("clowder.moods")
          .asScala
          .toList
          .map(mood => Mood(mood.get("name").render(), mood.get("emotion").render()))

      val clowderSize = config.getLong("clowder.size")

      log.info(s"Total $clowderSize cats to wakeup....")

      for (i <- 1L to clowderSize) {
        injectedChild(moodyCatActorFactory(possibleMoods, moodChangeInterval), s"Cat-$i")
      }
  }

  def scheduleCatOverlordship(): Unit = {
    timers.startSingleTimer(WakeUpCatsKey, WakeUpCats, 3.seconds)
  }
}
