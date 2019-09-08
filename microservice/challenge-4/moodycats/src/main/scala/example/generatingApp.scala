package example

import scala.concurrent.duration._
import scala.util.{Random, Try}
import akka.actor.ActorSystem

import scala.io.StdIn
import java.io.File

import akka.stream.{ActorMaterializer, IOResult}
import akka.stream.scaladsl._
import akka.util.ByteString

import scala.concurrent.Future

final case class AppConfig(catCount: Int,
                           lifetime: FiniteDuration,
                           file: File)

object Main extends App {

  implicit val rnd = new Random
  implicit val system = ActorSystem("MySystem")
  implicit val ec = system.dispatcher
  implicit val materializer = ActorMaterializer()
  implicit val log = system.log

  def configs(): Either[String, AppConfig] = {
    for {
      count <- StdIn.readLine("\n> how many cats?\n").toIntOption.toRight("Could not parse int")
      duration <- Try(Duration(StdIn.readLine("\n> Lifetime of this service?\n"))).toOption.toRight("Could not parse duration")
      fd <- Try(FiniteDuration(duration.length, duration.unit)).toOption.toRight("Duration is not finite")
      now <- Some(System.currentTimeMillis()).toRight("Couldn't get current time")
      file <- Try(File.createTempFile(s"MoodyCats-$now",".txt")).toOption.toRight("Could not create file")
      _ <- Right(Console.println("Generating moods in " + file.toPath))
    } yield AppConfig(count, fd, file)
  }

  def fileSink(file: File): Sink[ByteString, Future[IOResult]] = FileIO.toPath(file.toPath())

  configs()
    .flatMap(cf => {
      Right(Cats.moodSwings(cf.catCount)
        .takeWithin(cf.lifetime)
        .map(_.toString)
        .map(s => ByteString(s + "\n"))
        .runWith(fileSink(cf.file))
        .onComplete(f => {
          system.terminate()
        }))
    })
}
