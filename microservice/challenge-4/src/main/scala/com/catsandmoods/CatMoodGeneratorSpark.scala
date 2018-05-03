package com.catsandmoods

import java.util.concurrent.TimeUnit

import com.datastax.spark.connector._
import org.apache.spark.sql.SparkSession
import org.joda.time.DateTime

case class CatAndMood(name: String,
                      mood: String,
                      year: Long = 2018,
                      month: Long = 5,
                      day: Long = 2,
                      hour: Long = 12,
                      minute: Long = 14,
                      second: Long)

/**
  * cats-and-moods
  *
  */
object CatMoodGeneratorSpark {

  def main(args: Array[String]) {


    val now = new DateTime().withTimeAtStartOfDay().getMillis()
    val nbCats = 1000
    val totalSecondsInDay = 86400
    val delay = 27

    //TODO make properties configurable
    val spark = SparkSession.builder.master("local").appName("Generator Moods").getOrCreate()


    val cats = spark.sparkContext.parallelize((1 to nbCats), 10).map(num => Cat(num))
    val times = spark.sparkContext.parallelize(Seq.range(0, totalSecondsInDay, delay), 10)


    cats.cartesian(times).map[CatAndMood]{ case (cat,secondInDay) => {
      val date = new DateTime(now + TimeUnit.SECONDS.toMillis(secondInDay))

      CatAndMood(
        cat.name,
        cat.mood.toString,
        date.getYear,
        date.getMonthOfYear(),
        date.getDayOfMonth(),
        date.getHourOfDay(),
        date.getMinuteOfHour(),
        date.getSecondOfMinute())
    }}
      .saveToCassandra("cats", "moods", SomeColumns("name", "mood", "year", "month", "day", "hour", "minute", "second"))

    spark.stop()
  }
}
