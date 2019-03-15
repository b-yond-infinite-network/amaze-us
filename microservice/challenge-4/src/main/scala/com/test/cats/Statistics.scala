package com.test.cats

import java.io.File

import com.github.tototoshi.csv.CSVReader
import com.test.cats.data._
import scalaz.std.anyVal._
import scalaz.std.option._
import scalaz.syntax.foldable._
import scalaz.zio.console._
import scalaz.zio.{App, IO, Task, ZIO}
import scalaz.{Apply, NonEmptyList}

import scala.collection.mutable
import scala.util.Try

object Statistics extends App {

  case class Report(total: Long, average: Double, median: Double, variance: Double) {
    override def toString: String =
      s"""
         | Number of known moods: ${moods.all.size}
         | Total number of observed moods:           $total
         | Cats are in each mood on average (times): ${average.formatted("%.5f")}
         | Median value across all mods (times):     ${median.formatted("%.1f")}
         | Mood variance:                            ${variance.formatted("%.5f")}
       """.stripMargin
  }


  override def run(args: List[String]): ZIO[Environment, Nothing, Int] = {
    val program: IO[String, Report] = for {
      path   <- IO.fromEither(args.headOption.toRight("Run me with <path-to-file> argument"))
      counts <- consumeInput(new File(path))(countMoods).mapError(_.getMessage)
    } yield report(counts.map(_._2))

    program.either.flatMap(_.fold(
      error  => putStrLn(s"FAILED: $error").map(_ => 1),
      report => putStrLn(report.toString).map(_ => 0)
    ))
  }


  // Implementation is provided for file input,
  //  however it can be trivially replaced with DB or other streaming input
  //  keeping the same implementation and return type.

  def consumeInput[A](file: File)(process: Stream[Cat] => A): Task[A] =
    Task.bracket(
      Task(CSVReader.open(file))
    )(
      reader => Task(reader.close()).catchAll(_ => Task.unit)
    )(
      reader => Task(reader.toStream)
        .map(_
          .collect { case id :: mood :: _ =>
            Apply[Option].apply2(Try(id.toInt).toOption, moods.byDesc.get(mood))(Cat(_, _, 0))
          }
          .collect { case Some(cat) => cat }
        )
        .map(process)
    )

  type MoodCounts = NonEmptyList[(Mood, Long)]

  // 1. Input type is left as Seq to allow working with Stream.
  // 2. The use of mutable accumulator is an optimization.

  def countMoods(in: Seq[Cat]): MoodCounts = {
    val counted = in.foldLeft(mutable.Map(moods.all.toList.map(_ -> 0L): _*)) {
      (acc, cat) => acc.get(cat.mood).map(_ + 1).fold(acc)(acc += cat.mood -> _)
    }
    moods.all.map(m => m -> counted(m))
  }


  // Stats calculation logic is domain-independent (works with Long).
  // It does not depend on number of cat moods as well,
  //  e.g. if science discovers new mood it's simply a matter of adding
  //  another mood description into the model without modifying computing logic.
  // All list operations and safe (works with NonEmptyList).

  def report(counts: NonEmptyList[Long]): Report =
    Report(counts.suml, average(counts), median(counts), variance(counts))

  private def average(counts: NonEmptyList[Long]): Double =
    counts.suml.toDouble / counts.size

  private def median(counts: NonEmptyList[Long]): Double = {
    val size = counts.size
    val (unused, newSize) = if (size % 2 == 0) (size / 2 - 1, 2) else (size / 2, 1)
    counts.sorted.toList
      .drop(unused).dropRight(unused)
      .sum.toDouble / newSize
  }

  private def variance(counts: NonEmptyList[Long]): Double = {
    val mean = average(counts)
    counts.map(count => math.pow(count - mean, 2)).toList.sum / counts.size
  }
}
