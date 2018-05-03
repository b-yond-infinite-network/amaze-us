package com.catsandmoods

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.expressions.Window



object Reporting {

  def main(args: Array[String]) {

    val spark = SparkSession.builder.master("local").appName("Reporting Moods").getOrCreate()

    val catsAndMoodsCassandra =  spark
      .read
      .format("org.apache.spark.sql.cassandra")
      .options(Map("keyspace" -> "cats" , "table" -> "moods"))
      .load()

    catsAndMoodsCassandra.printSchema()
    //catsAndMoodsCassandra.show()

    //catsAndMoodsCassandra.groupBy("year").count().show(100)

   // catsAndMoodsCassandra.groupBy("year", "month", "mood").count().show(100)

    import org.apache.spark.sql.functions._
    val dfPerHour = catsAndMoodsCassandra
      //.groupBy("year", "month", "day", "hour", "mood")
        .withColumn("hourInDay", concat(col("year"), lpad(col("month"),2,"0"), lpad(col("day"),2,"0"), lpad(col("hour"),2,"0")))
      .withColumn("timestamp", unix_timestamp(col("hourInDay"), "yyyyMMddHH"))
      .groupBy("timestamp", "mood")
      .count()


    val win = Window.orderBy("timestamp")


    dfPerHour.persist()

    dfPerHour.show()
    dfPerHour
      .withColumn("rank", rank().over(Window.partitionBy("timestamp").orderBy(col("count").desc)))
      .show()

    dfPerHour.unpersist()
    spark.stop()
  }
}