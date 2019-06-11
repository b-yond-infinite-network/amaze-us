package actors.ingress

import java.util.concurrent.atomic.AtomicInteger

import actors.ingress.OverlordActor.WakeUpCats
import akka.actor.{Actor, ActorSystem, Props}
import akka.testkit.{ImplicitSender, TestKit}
import com.google.inject.AbstractModule
import com.typesafe.config.{Config, ConfigFactory}
import org.scalatest.{BeforeAndAfterAll, Matchers, WordSpecLike}
import play.api.inject.guice.GuiceInjectorBuilder
import play.api.libs.concurrent.AkkaGuiceSupport

import scala.concurrent.duration._

class TestModule extends AbstractModule
  with AkkaGuiceSupport {

  override def configure(): Unit = {
    bindActorFactory[MockMoodyCatActor, MoodyCatActor.Factory]
    val config = ConfigFactory.load()
    bind(classOf[Config]).toInstance(config)
  }
}

class MockMoodyCatActor extends Actor {

  override def preStart(): Unit = {
    OverlordActorSpec.NUMBER_OF_CATS_CREATED.incrementAndGet()
  }

  def receive = {
    case _ => {}
  }
}

object OverlordActorSpec {
  var NUMBER_OF_CATS_CREATED: AtomicInteger = new AtomicInteger()
}

class OverlordActorSpec extends TestKit(ActorSystem("MySpec"))
  with ImplicitSender
  with WordSpecLike
  with Matchers
  with BeforeAndAfterAll {

  override def afterAll: Unit = {
    TestKit.shutdownActorSystem(system)
  }

  "Overlord actor" must {
    "create n cats" in {
      val injector = new GuiceInjectorBuilder()
        .bindings(new TestModule)
        .injector()

      val factory = injector.instanceOf[MoodyCatActor.Factory]
      val config = injector.instanceOf[Config]
      val overlord = system.actorOf(Props(classOf[OverlordActor], factory, config))

      val expectedClowderSize = config.getInt("clowder.size")

      overlord ! WakeUpCats
      expectNoMessage(3.seconds)

      assert(OverlordActorSpec.NUMBER_OF_CATS_CREATED.get() == expectedClowderSize)
    }
  }
}
