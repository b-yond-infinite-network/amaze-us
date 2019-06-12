package controllers

import actors.egress.WebSocketActor
import akka.actor.{ActorRef, ActorSystem}
import akka.stream.Materializer
import javax.inject._
import play.api.libs.json.JsValue
import play.api.libs.streams.ActorFlow
import play.api.mvc._

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class HomeController @Inject()(cc: ControllerComponents)(implicit system: ActorSystem,
                                                         mat: Materializer,
                                                         @Named("aggregation-results-actor") aggrResultsActor: ActorRef)
  extends AbstractController(cc) {

  val logger = play.api.Logger(getClass)

  /**
    * A web socket handler where clients can connect to stream in cat moods
    * @return
    */
  def moodtrends: WebSocket = WebSocket.accept[JsValue, JsValue] { request =>
    ActorFlow.actorRef { out =>
      WebSocketActor.props(out, aggrResultsActor)
    }
  }
}
