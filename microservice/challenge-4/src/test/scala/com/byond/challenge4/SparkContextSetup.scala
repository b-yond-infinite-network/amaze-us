package com.byond.challenge4

import org.apache.spark.sql.SparkSession

trait SparkContextSetup {

  def withSparkContext(testMethod: SparkSession => Any): Any = {

    val ss = SparkSession
      .builder()
      .master("local[*]")
      .appName("MedianTest")
      .getOrCreate()

    testMethod(ss)
  }
}
