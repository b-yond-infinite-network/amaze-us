package example

import org.specs2.mutable.Specification
import Cats._
import Cats.Mood._

import scala.util.Random
import akka.testkit._
import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.stream.scaladsl._

import scala.concurrent.Await
import scala.concurrent.duration._

class CatsSpec extends Specification {
  implicit val system = ActorSystem("MySystem")
  implicit val ec = system.dispatcher
  implicit val materializer = ActorMaterializer()

  "cats moods are unpredictable" >> {
    implicit val rnd = new Random(0)

    val future = Cats.moods(5).runWith(Sink.fold(Seq.empty[MoodSwing])(_ :+ _))

    val result = Await.result(future, 1.second)

    result.map(_.id) should equalTo(Seq(1,2,3,4,5))

    // The "random" results with seed of 0
    result.map(_.mood) should equalTo(Seq(ROLLONFLOOR, SCRATCHCHAIRS, GROWL, THROWGLASS, ROLLONFLOOR))
  }

  "Mood swings are timed like clockwork" >> {

    val future = Cats.change(20.milliseconds).takeWithin(110.milliseconds).runWith(Sink.fold(Seq.empty[Int])(_ :+ _))

    val result = Await.result(future, 1.second)

    result should have length(6)
  }

  "Mood swings should be random and repeated for every cat" >> {
    implicit val rnd = new Random(0)

    val future = Cats.moodSwings(3, 20.milliseconds).takeWithin(110.milliseconds).runWith(Sink.fold(Seq.empty[MoodSwing])(_ :+ _))

    val result = Await.result(future, 1.second)

    // 3 cats, changing moods 6 times
    result should have length(18)

    result.map(_.id).take(5) should equalTo(Seq(1,2,3,1,2))

    // The "random" results with seed of 0
    result.map(_.mood).take(5) should equalTo(Seq(ROLLONFLOOR, SCRATCHCHAIRS, GROWL, THROWGLASS, ROLLONFLOOR))
  }
}
