package actors.egress

import java.util.concurrent.TimeUnit

import actors.egress.AggregationResultsActor.{CountResult, RegisterMe, UnRegisterMe}
import akka.actor.{Actor, ActorLogging, ActorRef, Props}
import akka.util.Timeout
import play.api.libs.json._

import scala.concurrent.duration.FiniteDuration
import scala.concurrent.{ExecutionContext, ExecutionContextExecutor}

object WebSocketActor {
  implicit val ec: ExecutionContextExecutor = ExecutionContext.global
  implicit val timeout: Timeout = Timeout(FiniteDuration(1, TimeUnit.SECONDS))
  def props(out: ActorRef, in: ActorRef) = Props(new WebSocketActor(out, in))
}

/**
  * An actor tied to each web socket connection.
  * On-connect, registers with the aggregation actor to receive results.
  * Returns the results to the actor created for the web socket.
  * @param out - An actor created to receive results
  * @param in - Actor to register for results
  */
class WebSocketActor(out: ActorRef, in: ActorRef) extends Actor with ActorLogging {

  implicit val residentWrites = new Writes[CountResult] {
    def writes(countResult: CountResult): JsObject = Json.obj(
      "mood" -> countResult.mood,
      "count" -> countResult.count,
    )
  }

  override def preStart(): Unit = {
    in ! RegisterMe
  }

  override def receive: Receive = {
    case countResult: Array[CountResult] =>
      val topMoods: JsValue = Json.toJson(countResult)
      val output: JsValue = Json.obj(
        "type" -> JsString("count"),
        "topMoods" -> topMoods
      )
      out ! output
  }

  override def postStop(): Unit = {
    in ! UnRegisterMe
  }
}
