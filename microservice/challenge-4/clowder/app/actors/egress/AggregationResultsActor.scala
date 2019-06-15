package actors.egress

import actors.egress.AggregationResultsActor.{StatisticsResult, CountResult, RegisterMe, UnRegisterMe}
import akka.actor.{Actor, ActorRef}

object AggregationResultsActor {
  case class CountResult(mood: String, count: Long)
  case class StatisticsResult(emotionName: String, mean: Double, variance: Double)
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
    case countResult: Array[CountResult] =>
      registeredWebSocketActors.foreach(entry => entry._2 ! countResult)
    case aggrResult: Array[StatisticsResult] =>
      registeredWebSocketActors.foreach(entry => entry._2 ! aggrResult)
    case RegisterMe =>
      registeredWebSocketActors += sender().path.name -> sender()
    case UnRegisterMe =>
      registeredWebSocketActors -= sender().path.name
  }
}
