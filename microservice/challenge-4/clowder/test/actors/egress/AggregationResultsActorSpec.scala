package actors.egress

import actors.egress.AggregationResultsActor.{CountResult, RegisterMe, StatisticsResult}
import akka.actor.{ActorSystem, Props}
import akka.testkit.{ImplicitSender, TestKit, TestProbe}
import org.scalatest.{BeforeAndAfterAll, Matchers, WordSpecLike}
import scala.concurrent.duration._

class AggregationResultsActorSpec extends TestKit(ActorSystem("MySpec"))
  with ImplicitSender
  with WordSpecLike
  with Matchers
  with BeforeAndAfterAll {

  override def afterAll: Unit = {
    TestKit.shutdownActorSystem(system)
  }

  "On receiving count result" must {

    "send count results to each registered actor" in {

      val webSocketConnectionActor1 = TestProbe()
      val webSocketConnectionActor2 = TestProbe()

      val aggregationActor = system.actorOf(Props[AggregationResultsActor])

      //register two web socket actors
      aggregationActor.tell(RegisterMe, webSocketConnectionActor1.ref)
      aggregationActor.tell(RegisterMe, webSocketConnectionActor2.ref)

      //send count results to AggregationResultsActor
      val countResults = Array(CountResult("some mood", 10))
      aggregationActor ! countResults

      //count results should be received by both web socket actors
      webSocketConnectionActor1.expectMsg(500 millis, countResults)
      webSocketConnectionActor2.expectMsg(500 millis, countResults)
    }

    "send statistics to each registered actor" in {

      val webSocketConnectionActor1 = TestProbe()
      val webSocketConnectionActor2 = TestProbe()

      val aggregationActor = system.actorOf(Props[AggregationResultsActor])

      //register two web socket actors
      aggregationActor.tell(RegisterMe, webSocketConnectionActor1.ref)
      aggregationActor.tell(RegisterMe, webSocketConnectionActor2.ref)

      //send stats results to AggregationResultsActor
      val statsResults = Array(StatisticsResult("some name", 5D, 5D))
      aggregationActor ! statsResults

      //stats results should be received by both web socket actors
      webSocketConnectionActor1.expectMsg(500 millis, statsResults)
      webSocketConnectionActor2.expectMsg(500 millis, statsResults)
    }
  }
}
