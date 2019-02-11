package com.regisleray

import java.text.NumberFormat

import cats.{Show, effect}
import cats.effect.{IO, Timer}
import com.regisleray.Cat.Mood
import com.regisleray.db.{DBSupport, Db}
import fs2.Stream
import cats.syntax.show._

import scala.concurrent.ExecutionContext
import scala.concurrent.duration._
import com.regisleray.db.syntax._

case class Stats(moods: List[(Mood, Int)]){
  val average: List[(Mood, Double)] = Stats.avg(moods)
  val median: Option[Double] = Stats.median(moods)
  val variance: Option[Double] = Stats.variance(moods)
}

object Stats {
  def asPercentage(d: Double) = NumberFormat.getPercentInstance.format(d)

  implicit val showStats: Show[Stats] = (t: Stats) => f"average ${t.average.map(a => a._1 -> asPercentage(a._2))} :: median ${t.median.getOrElse(0d)}%.02f :: variance ${t.variance.getOrElse(0d)}%.02f"

  def avg(moods: List[(Mood, Int)]): List[(Mood, Double)] = {
    val total = moods.map(_._2).sum.toDouble
    moods.map(m => m._1 -> m._2 / total)
  }

  def median(moods: List[(Mood, Int)]): Option[Double] = {
    val (lower, upper) = moods.sortWith((l, r) => l._2 < r._2)
      .splitAt(moods.size / 2)

    Option(moods.size)
      .filter(_ > 0)
      .filter(_ % 2 == 0)
      .map(_ => (lower.last._2 + upper.head._2) / 2.0d)
      .map(Some(_))
      .getOrElse(upper.headOption.map(_._2.toDouble))
  }

  private def mean(xs: Seq[Double]): Option[Double] =
    if (xs.isEmpty) None
    else Some(xs.sum / xs.length)

  def variance(moods: List[(Mood, Int)]): Option[Double] = {
    val xs = moods.map(_._2.toDouble)
    mean(xs).flatMap(m => mean(xs.map(x => Math.pow(x - m, 2))))
  }
}

object StatApp{
  implicit val timer: Timer[effect.IO] = IO.timer(ExecutionContext.global)

  def main(args: Array[String]): Unit = doRun.unsafeRunSync()

  def doRun: IO[Unit] = for {
    _ <- DBSupport.setup()
    _ <- Stream.fixedDelay[IO](2 seconds)
      .evalMap(_ => Db.selectAll.exec)
      .map(Stats(_))
      .evalMap(s => IO(println(s.show)))
      .compile
      .drain
  } yield ()
}


