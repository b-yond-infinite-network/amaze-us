package com.byond.challenge4

import com.byond.challenge4.cats.Cat
import com.byond.challenge4.udaf.MedianUDAF
import org.scalatest.{ShouldMatchers, WordSpecLike}

class StatsSpec extends ShouldMatchers with WordSpecLike with SparkContextSetup {

  val median = new MedianUDAF("test")

  "A Stats generations" must {

    "expect to generate stats" in withSparkContext { ss =>
      import ss.implicits._

      val data   = Seq(Cat("A",1), Cat("B",1), Cat("C",2), Cat("D",3), Cat("E", 3))

      val df     = ss.sparkContext.parallelize(data).toDF
      val result = Stats.generateStats(df)
      val first    = result.first()
      val avg      = first.get(0)
      val mean     = first.get(1)
      val median   = first.get(2)
      val variance = first.get(3)

      avg      shouldEqual 2.0
      mean     shouldEqual 2.0
      median   shouldEqual 2.0
      variance shouldEqual 1.0
    }

    "expect to generate stats 2" in withSparkContext { ss =>
      import ss.implicits._

      val data   = Seq(Cat("A",1), Cat("B",1), Cat("C",2), Cat("D",3), Cat("E", 3),
        Cat("F",4), Cat("G",7), Cat("H",5), Cat("I",2), Cat("J", 3), Cat("K", 7))

      val df     = ss.sparkContext.parallelize(data).toDF
      val result = Stats.generateStats(df)
      val first    = result.first()
      val avg      = first.get(0)
      val mean     = first.get(1)
      val median   = first.get(2)
      val variance = first.get(3)

      avg      shouldEqual 3.6666666666666665
      mean     shouldEqual 3.6666666666666665
      median   shouldEqual 3.5
      variance shouldEqual 4.666666666666666
    }

  }
}