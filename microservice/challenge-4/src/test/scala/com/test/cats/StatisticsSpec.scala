package com.test.cats

import java.io.File

import org.specs2.mutable.Specification
import scalaz.NonEmptyList
import scalaz.std.anyVal._
import scalaz.syntax.foldable._
import scalaz.zio.DefaultRuntime

class StatisticsSpec extends Specification {

  import Statistics._
  import data._

  "Counting moods" >> {
    "have count for every known mood" in {
      countMoods(Nil).size must_=== moods.all.size
      countMoods(List(Cat(0, moods.HISS, 0))).size must_=== moods.all.size
    }

    "aggregate correctly" in {
      val cc = countMoods(List(
        Cat(0, moods.HISS, 0),
        Cat(0, moods.HISS, 0),
      )).toList.toMap
      moods.all.map {
        case moods.HISS => cc.get(moods.HISS) must beSome(2L)
        case m          => cc.get(m) must beSome(0L)
      }.toList.reduce(_ and _)
    }
  }

  "Computing stats" >> {
    report(NonEmptyList(1))             must_=== Report(1, 1, 1, 0)
    report(NonEmptyList(1, 2))          must_=== Report(3, 1.5, 1.5, 0.25)
    report(NonEmptyList(1, 2, 3, 4))    must_=== Report(10, 2.5, 2.5, 1.25)

    report(NonEmptyList(1, 2, 3, 4, 5)) must_=== Report(15, 3, 3, 2)
    report(NonEmptyList(5, 5, 0, 5, 5)) must_=== Report(20, 4, 5, 4)
    report(NonEmptyList(5, 0, 0, 0, 0)) must_=== Report(5, 1, 0, 4)
  }

  private val runtime = new DefaultRuntime {}

  "Loading from file" >> {
    "load correct cat moods and ignore malformed" in {
      val counts = runtime.unsafeRun(
        consumeInput(new File(getClass.getClassLoader.getResource("sample").getFile))(countMoods)
      )
      counts.size must_=== moods.all.size
      counts.map(_._2).suml must_=== 2
    }
  }
}
