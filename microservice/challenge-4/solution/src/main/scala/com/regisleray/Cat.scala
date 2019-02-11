package com.regisleray

import cats.effect
import cats.effect.{ContextShift, IO, Timer}
import com.regisleray.db.syntax._
import com.regisleray.db.{DBSupport, Db}
import fs2.Stream

import scala.concurrent.ExecutionContext
import scala.concurrent.duration._
import scala.util.Random

object Cat {
  sealed trait Mood

  object Mood {
    case object Growl extends Mood
    case object Hiss extends Mood
    case object Puur extends Mood
    case object Throwglass extends Mood
    case object RollOnFloor extends Mood
    case object ScratchChairs extends Mood
    case object LookDeepInEyes extends Mood

    val All: List[Mood] = List(Growl, Hiss, Puur, Throwglass, RollOnFloor, ScratchChairs, LookDeepInEyes)

    def from(v: String): Either[Exception, Mood] = {
      All.map(m => m.toString.toLowerCase -> m)
        .toMap
        .get(v.toLowerCase)
        .toRight(new Exception(s"Cannot found $v in moods $All"))
    }

    def random: Mood = Random.shuffle(All).head
  }

  def create(wait: FiniteDuration = 27 seconds)(implicit t: Timer[IO]): Stream[IO, Mood] =
    Stream.fixedDelay[IO](wait).map(_ => Mood.random)
}

object CatApp{
  val MaxCats = 1000
  implicit val timer: Timer[effect.IO] = IO.timer(ExecutionContext.global)
  implicit val cs: ContextShift[IO] = IO.contextShift(scala.concurrent.ExecutionContext.Implicits.global)

  def main(args: Array[String]): Unit = doRun.unsafeRunSync()

  def doRun: IO[Unit] = for {
      _ <- DBSupport.setup()
      _ <- (1 to MaxCats).map(_ => Cat.create())
      .reduceLeft(_ merge _)
      .evalMap(Db.upsert(_).exec)
      .compile.drain
    } yield ()
}


