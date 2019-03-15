package com.test.cats

import java.io.File
import java.util.concurrent.TimeUnit

import com.github.tototoshi.csv.CSVWriter
import com.test.cats.data._
import scalaz.std.iterable._
import scalaz.std.option._
import scalaz.syntax.foldable._
import scalaz.zio._
import scalaz.zio.clock._
import scalaz.zio.console._
import scalaz.zio.duration._
import scalaz.zio.random._
import scalaz.zio.stream.Stream
import scalaz.{Apply, Monoid}

import scala.util.Try

object Observation extends App {

  type Cats = Stream[Random with Clock, Nothing, Cat]

  case class Config(herdSize: Int, moodChangeInterval: Duration, runTime: Duration)


  // Main program flow:
  //  parse input params,
  //  create app config,
  //  build stream of data and run it with a file sink.

  override def run(args: List[String]): ZIO[Environment, Nothing, Int] = {
    val params = args match {
      case herdSize :: duration :: _ =>
        Apply[Option].tuple2(Try(herdSize.toInt).toOption, parseDuration(duration))
      case _ =>
        None
    }

    def program(ts: Timestamp): IO[String, String] = for {
      params   <- IO.fromEither(params.toRight("Run me with <number-of-cats> <duration> arguments"))
      tempDir  <- IO(System.getProperty("java.io.tmpdir")).mapError(_.getMessage)
      (herdSize, duration) = params
      file                 = new File(new File(tempDir), s"cats-observation-$ts")
      config               = Config(herdSize, 27.seconds, duration)
      _        <- persist(file)(observation(config)).mapError(_.getMessage)
    } yield file.getAbsolutePath

    for {
      timestamp <- currentTime(TimeUnit.MILLISECONDS)
      result    <- program(timestamp).either
      ret       <- result.fold(
        error => putStrLn(s"FAILED: $error").map(_ => 1),
        path  => putStrLn(s"Saved to $path").map(_ => 0)
      )
    } yield ret
  }

  // Data from the stream is continuously dumped into csv file.

  def persist(file: File)(cats: Cats): Task[Unit] =
    Task.bracket(
      Task(CSVWriter.open(file))
    )(
      writer => Task(writer.close()).catchAll(_ => Task.unit)
    )(
      writer => Task(unsafeRun(cats.foldLeft(())((_, cat) => writer.writeRow(List(cat.id, cat.mood.desc, cat.ts)))))
    )


  // Observing a cat changing mood is modeled as a stream of data
  //  with cat states emitted from the stream at time interval according to provided config.
  //  Thus observing many cats is simply a merge of many streams into one.

  import implicits._

  def cat(id: Int): ZIO[Clock with Random, Nothing, Cat] =
    for {
      mood <- nextInt(7)
      timestamp <- currentTime(TimeUnit.MILLISECONDS)
    } yield Cat(id, moods.byIndex(mood), timestamp)

  def catMoods(changeEvery: Duration, observeFor: Duration)(id: Int): Cats =
    Stream
      .lift(cat(id))
      .repeat(Schedule.spaced(changeEvery) && Schedule.duration(observeFor))

  def observation(config: Config): Cats =
    (1 to config.herdSize)
      .foldMap[Cats](catMoods(config.moodChangeInterval, config.runTime))


  private def parseDuration(v: String): Option[Duration] =
    Try(Duration.fromScala(scala.concurrent.duration.Duration(v))).toOption

  object implicits {
    implicit val mergeStreams: Monoid[Cats] = new Monoid[Cats] {
      override def zero: Cats = Stream.empty
      override def append(f1: Cats, f2: => Cats): Cats = f1 merge f2
    }
  }
}
