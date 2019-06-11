package actors.egress

import actors.egress.AggregationResultsActor.{CountResult, RegisterMe, UnRegisterMe}
import akka.actor.{Actor, ActorRef}

object AggregationResultsActor {
  case class CountResult(mood: String, count: Long)
  object RegisterMe
  object UnRegisterMe
}

/**
  * An actor to receive results for all aggregations.
  * This actor also holds a map of all web socket actors.
  * Each new web socket creates a child actor which registers/unregisters here.
  * This allows us to stream statistics to multiple clients
  */
class AggregationResultsActor extends Actor {

  private val registeredWebSocketActors = collection.mutable.Map[String, ActorRef]()

  override def receive: Receive = {
    case result: Array[CountResult] =>
      registeredWebSocketActors.foreach(entry => entry._2 ! result)
    case RegisterMe =>
      registeredWebSocketActors += sender().path.name -> sender()
    case UnRegisterMe =>
      registeredWebSocketActors -= sender().path.name
  }
}
