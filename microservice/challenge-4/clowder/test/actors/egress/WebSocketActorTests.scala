package actors.egress

import actors.egress.AggregationResultsActor.{CountResult, StatisticsResult}
import akka.actor.{ActorSystem, Props}
import akka.testkit.{ImplicitSender, TestKit, TestProbe}
import org.scalatest.{BeforeAndAfterAll, Matchers, WordSpecLike}
import play.api.libs.json.{JsValue, Json}

import scala.concurrent.duration._

class WebSocketActorTests extends TestKit(ActorSystem("MySpec"))  with ImplicitSender
  with WordSpecLike
  with Matchers
  with BeforeAndAfterAll {

  override def afterAll: Unit = {
    TestKit.shutdownActorSystem(system)
  }

  "When count results received" must {
    "send them to web socket" in {
      val probe = TestProbe()
      val webSocketActor = system.actorOf(Props(new WebSocketActor(probe.ref, self)))
      val countResults = Array(CountResult("some mood", 10))
      webSocketActor ! countResults

      val expectedJson: JsValue = Json.parse(
        """
          |  {
          |    "type" : "count",
          |    "topMoods" : [ {
          |      "mood" : "some mood",
          |      "count" : 10
          |    }]
          |  }
        """.stripMargin)

      probe.expectMsg(500 millis, expectedJson)
    }
  }

  "When statistics results received" must {
    "send them to web socket" in {
      val probe = TestProbe()
      val webSocketActor = system.actorOf(Props(new WebSocketActor(probe.ref, self)))
      val statsResults = Array(StatisticsResult("some mood", 5D, 5D))
      webSocketActor ! statsResults

      val expectedJson: JsValue = Json.parse(
        """
          |  {
          |    "type" : "statistics",
          |    "moodStatistics" : [ {
          |      "mood" : "some mood",
          |      "mean" : 5,
          |      "variance": 5
          |    }]
          |  }
        """.stripMargin)

      probe.expectMsg(500 millis, expectedJson)
    }
  }
}
