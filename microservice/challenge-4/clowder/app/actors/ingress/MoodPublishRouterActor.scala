package actors.ingress

import actors.ingress.MoodPublisher.PublishedMood
import akka.actor.{Actor, ActorLogging}
import akka.routing.{ActorRefRoutee, RoundRobinRoutingLogic, Router}
import com.typesafe.config.Config
import javax.inject.Inject
import play.api.libs.concurrent.InjectedActorSupport

/**
  * A router to fan-out cat moods to be published
  * @param moodPublisherFactory - Factory to create mood publishers
  */
class MoodPublishRouterActor @Inject() (moodPublisherFactory: MoodPublisher.Factory, config: Config) extends Actor
  with InjectedActorSupport with ActorLogging {

  var router: Router = {
    val routees =
      (1 to config.getInt("mood.fanout"))
        .map(x => {
          val actorRef = injectedChild(moodPublisherFactory(), s"MoodPublisher-$x")
          context watch actorRef
          ActorRefRoutee(actorRef)
        })
    Router(RoundRobinRoutingLogic(), routees)
  }

  override def receive: Receive = {
    case mood: PublishedMood =>
      router.route(mood, sender())
  }
}
