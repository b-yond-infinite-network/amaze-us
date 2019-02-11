package com.regisleray

import cats.effect
import cats.effect.{ContextShift, IO, Timer}
import com.regisleray.Cat.Mood
import org.scalatest.{Matchers, WordSpec}

import scala.concurrent.ExecutionContext
import scala.concurrent.duration._
import fs2.concurrent.SignallingRef
import org.scalatest.concurrent.Eventually

class CatTest extends WordSpec with Matchers with Eventually {

  "Mood" should {
    "from succeed" in {
      Mood.All.foreach { m =>
        Mood.from(m.toString) shouldBe Right(m)
      }
    }

    "from failed" in {
      Mood.from("blah").left.get.getMessage should startWith(s"Cannot found blah in moods")
    }
  }

  "Cat" should {
    implicit val timer: Timer[effect.IO] = IO.timer(ExecutionContext.global)
    implicit val cs: ContextShift[IO] = IO.contextShift(scala.concurrent.ExecutionContext.Implicits.global)

    "create a cat with different moods" in {
      val shutdown = SignallingRef[IO, Boolean](false).unsafeRunSync()

      var moods = Seq.empty[Mood]

      Cat.create(10 milliseconds)
        .map(m => moods = moods :+ m)
        .interruptWhen(shutdown)
        .compile.drain.unsafeToFuture().isCompleted shouldBe false

      eventually(timeout(1 seconds)) {
        moods should contain allElementsOf Mood.All
        shutdown.set(true).unsafeRunSync() shouldBe ((): Unit)
      }
    }

  }

}
