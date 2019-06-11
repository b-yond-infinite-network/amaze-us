package actors.ingress

import akka.actor.{Actor, ActorLogging}
import com.typesafe.config.Config
import javax.inject.Inject
import play.api.libs.concurrent.InjectedActorSupport
import collection.JavaConverters._

object OverlordActor {
  object WakeUpCats
  case class Mood (name: String, emotion: String)
}

/**
  * An actor responsible for creating cats in the world.
  * @param moodyCatActorFactory - A factory to create cats
  * @param config - type space config
  */
class OverlordActor @Inject() (moodyCatActorFactory: MoodyCatActor.Factory, config: Config)
  extends Actor with InjectedActorSupport with ActorLogging {

  import OverlordActor._

  override def receive: Receive = {
    case WakeUpCats =>

      log.info("Waking up the clowder.....")

      val moodChangeInterval = config.getDuration("clowder.moodchange")
      val possibleMoods =
        config.getObjectList("clowder.moods")
          .asScala
          .toList
          .map(x =>
            Mood(
              x.get("name").render(),
              x.get("emotion").render()
            )
          )

      val clowderSize = config.getLong("clowder.size")

      log.info(s"Total $clowderSize cats to wakeup....")

      for (i <- 1L to clowderSize) {
        injectedChild(moodyCatActorFactory(possibleMoods, moodChangeInterval), s"Cat-$i")
      }
  }
}
