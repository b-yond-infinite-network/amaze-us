import akka.actor.ActorSystem
import akka.testkit.{TestActors, TestKit, TestProbe}
import entities.{Cat, Moods}
import org.scalatest.{BeforeAndAfterAll, Matchers, WordSpecLike}

import scala.language.postfixOps


class CatzSpec(_system: ActorSystem) extends TestKit(_system)
    with Matchers with WordSpecLike with BeforeAndAfterAll {

    def this() = this(ActorSystem("CatzSpec"))

    override def afterAll: Unit = {
        shutdown(system)
    }

    "A Cat Actor" should {
        "reply with the default mood if no mood change has occurred" in {
            val probe = TestProbe()
            val echoProducer = system.actorOf(TestActors.echoActorProps)
            val catActor = system.actorOf(Cat.props(69, echoProducer))

            catActor.tell(Cat.AskMood, probe.ref)

            val response = probe.expectMsgType[Cat.ReportMood]
            response.mood should ===(Moods.DEFAULT)
        }

        "change its mood when asked to and eventually have a non-default mood" in {
            val probe = TestProbe()
            val echoProducer = system.actorOf(TestActors.echoActorProps)
            val catActor = system.actorOf(Cat.props(69, echoProducer))

            catActor.tell(Cat.ChangeMood, probe.ref)
            catActor.tell(Cat.AskMood, probe.ref)

            var response = probe.expectMsgType[Cat.ReportMood]

            while (response.mood == Moods.DEFAULT) {
                catActor.tell(Cat.ChangeMood, probe.ref)
                catActor.tell(Cat.AskMood, probe.ref)
                response = probe.expectMsgType[Cat.ReportMood]
            }

            response.mood should !==(Moods.DEFAULT)
        }
    }
}
