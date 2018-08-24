package com.byond.challenge4.udaf

import com.byond.challenge4.SparkContextSetup
import org.apache.spark.sql.functions.col
import org.scalatest.{ShouldMatchers, WordSpecLike}


class MedianSpec extends ShouldMatchers with WordSpecLike with SparkContextSetup {

  val median = new MedianUDAF("test")

  "A Median UADF" must {

    "expect to compute median value of an odd number of rows" in withSparkContext { ss =>
      import ss.implicits._

      val data   = Seq(1, 1, 2, 6, 5, 6, 3)
      val rdd    = ss.sparkContext.parallelize(data)
      val df     = rdd.toDF
      val result = df.agg(median(col("value"))).collect()

      result(0).get(0) shouldBe 3.0
    }

    "expect to compute median value of an even number of rows" in withSparkContext { ss =>
      import ss.implicits._
      val data   = Seq(1, 1, 2, 6, 5, 6, 3, 4)
      val rdd    = ss.sparkContext.parallelize(data)
      val df     = rdd.toDF
      val result = df.agg(median(col("value"))).collect()

      result(0).get(0) shouldBe 3.5
    }

  }
}