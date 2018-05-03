package com.catsandmoods

import org.apache.spark.serializer.KryoSerializer
import org.apache.spark.sql.{Row, SparkSession}
import org.apache.spark.sql.types.{IntegerType, StringType, StructField, StructType}
import org.scalatest.{FlatSpec, Matchers}
import org.apache.spark.sql.functions.col
class ReportingTest extends FlatSpec with Matchers{

  import Fixture._
  "groupByHourAndCount" should "group data per hour" in {

    //system under test
    val actual = Reporting.groupByHourAndCount(catAndMoodsDF)

    actual.count() should be (3)
    actual.where(col("mood") === "MIAW").first().getAs[Int]("count") should be (2)
    actual.where(col("mood") === "GROWL").first().getAs[Int]("count") should be (1)
    actual.where(col("mood") === "THROWGLASS").first().getAs[Int]("count") should be (1)
  }

  "getRankPerHour" should "rank mood over time" in {

    val alreadyGroupPerHourDF = Reporting.groupByHourAndCount(catAndMoodsDF)

    //sut
    val actual = Reporting.getRankPerHour(alreadyGroupPerHourDF)

    actual.count() should be (3)
    actual.where(col("mood") === "MIAW").first().getAs[Int]("rank") should be (1)
    actual.where(col("mood") === "GROWL").first().getAs[Int]("rank") should be (2)
    actual.where(col("mood") === "THROWGLASS").first().getAs[Int]("rank") should be (1)
  }

}


object Fixture {

  val ss = SparkSession
    .builder()
    .config("spark.serializer", classOf[KryoSerializer].getCanonicalName)
    .master("local[*]")
    .getOrCreate()

  private val catAndMoodsRDD = ss.sparkContext.parallelize(
    Seq(Row("cat-1","MIAW", 2018,5,3,0,0,0),
      Row("cat-1","GROWL", 2018,5,3,0,0,27),
      Row("cat-1","THROWGLASS", 2018,5,3,1,0,21),
      Row("cat-2","MIAW", 2018,5,3,0,0,0)))
  private val catAndMoodsSchema = StructType(
    StructField("name", StringType, false) ::
      StructField("mood", StringType, false) ::
      StructField("year", IntegerType, false) ::
      StructField("month", IntegerType, false) ::
      StructField("day", IntegerType, false) ::
      StructField("hour", IntegerType, false) ::
      StructField("minute", IntegerType, false) ::
      StructField("second", IntegerType, false) :: Nil)
  val catAndMoodsDF = ss.createDataFrame(catAndMoodsRDD, catAndMoodsSchema)
}