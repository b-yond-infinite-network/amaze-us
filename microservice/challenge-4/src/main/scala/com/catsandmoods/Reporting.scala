package com.catsandmoods

import org.apache.spark.sql.{DataFrame, SparkSession}
import org.apache.spark.sql.expressions.Window
import org.apache.spark.sql.functions._
import org.apache.spark.storage.StorageLevel


object Reporting {


  //TODO Columns names as variables

  def groupByHourAndCount(catsAndMoodDF : DataFrame) : DataFrame = {
    catsAndMoodDF
    .withColumn("hourInDay", concat(col("year"), lpad(col("month"),2,"0"), lpad(col("day"),2,"0"), lpad(col("hour"),2,"0")))
      .withColumn("timestamp", unix_timestamp(col("hourInDay"), "yyyyMMddHH"))
      .groupBy("timestamp", "mood")
      .count()
  }

  def getRankPerHour( catsAndMoodsDF : DataFrame) : DataFrame = {
    catsAndMoodsDF.withColumn("rank", rank().over(Window.partitionBy("timestamp").orderBy(col("count").desc)))
  }


 /*
    how many cats in a specific mood for a specific hour compared to mean
  */
  def getMeanPerHour( catsAndMoodsDF : DataFrame) : DataFrame = {
    catsAndMoodsDF
      .withColumn("mean", mean("count").over(Window.partitionBy("mood")))
  }

  //FixMe this doesn t give us a coherent result, needs investigation
  def getVariance( catsAndMoodsDF : DataFrame) : DataFrame = {
    catsAndMoodsDF
      .withColumn("mean", mean("count").over(Window.partitionBy("mood")))
      .withColumn("ecart", pow(col("count")-col("mean"),2))
      .groupBy("mood")
      .agg((sum("ecart")/8).alias("variance"))
  }

  def main(args: Array[String]) {

    val spark = SparkSession.builder.master("local").appName("Reporting Moods").getOrCreate()

    val catsAndMoodsCassandra =  spark
      .read
      .format("org.apache.spark.sql.cassandra")
      .options(Map("keyspace" -> "cats" , "table" -> "moods"))
      .load()

    //catsAndMoodsCassandra.printSchema()
    //catsAndMoodsCassandra.show()
    //catsAndMoodsCassandra.groupBy("year").count()
   // catsAndMoodsCassandra.groupBy("year", "month", "mood").count()

    val dfPerHour = groupByHourAndCount(catsAndMoodsCassandra)
    dfPerHour.persist(StorageLevel.MEMORY_AND_DISK)

    //dfPerHour.show()

    getRankPerHour(dfPerHour).show()
    getMeanPerHour(dfPerHour).show()
    getVariance(dfPerHour).show()
    dfPerHour.unpersist()
    spark.stop()
  }
}