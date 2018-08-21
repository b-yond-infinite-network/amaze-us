package com.byond.challenge4

import com.byond.challenge4.cats.Cat
import com.byond.challenge4.configuration.{SensorSettings, Settings}
import com.byond.challenge4.sensor.CatMoodSensor
import com.typesafe.config.ConfigFactory
import net.ceedubs.ficus.Ficus._
import net.ceedubs.ficus.readers.ArbitraryTypeReader._
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.functions._
import org.apache.spark.sql.{DataFrame, SparkSession}
import org.apache.spark.streaming.dstream.DStream
import org.apache.spark.streaming.kafka010.ConsumerStrategies.Subscribe
import org.apache.spark.streaming.kafka010.KafkaUtils
import org.apache.spark.streaming.kafka010.LocationStrategies.PreferConsistent
import org.apache.spark.streaming.{Duration, StreamingContext}
import org.log4s._

object Mysteries {

  protected[this] val MOOD_COLUMN_NAME: String  = "mood"
  protected[this] val COUNT_COLUMN_NAME: String = "count"

  @transient
  protected[this] lazy val logger: Logger = getLogger(getClass)

  @transient
  private[this] lazy val config           = ConfigFactory.load()

  @transient
  private[this] lazy val settings         =  config.as[Settings](getClass.getName.replace("$", ""))

  @transient
  private[this] lazy val batchDuration    = Duration(settings.kafka.batchDuration.toMillis)

  @transient
  private[this] lazy val producer: KafkaProducer[String, String] = new KafkaProducer[String, String](settings.kafkaProps)

  @transient
  private[this] lazy val sensorSettings   = SensorSettings(producer, settings.kafka.topics.head,
    settings.akka.catsToSense, settings.akka.intervalDuration)

  def main(args: Array[String]): Unit = {

    logger.info("Starting application...")
    new CatMoodSensor(sensorSettings)
      .start()

    val ss = SparkSession
      .builder()
      .appName(settings.appName)
      .getOrCreate()

    val ssc = new StreamingContext(ss.sparkContext, batchDuration)

    //Create streams
    logger.info("create streams")
    val stream = sensorAllCatMoods(ssc)

    logger.info("process streams")
    processStream(ss, stream)

    //Start streams
    logger.info("start streams")
    ssc.start()
    ssc.awaitTermination()

  }

  private[this] def sensorAllCatMoods(ssc: StreamingContext): DStream[Cat] = {
    KafkaUtils.createDirectStream[String, String](
      ssc,
      PreferConsistent,
      Subscribe[String, String](
        settings.kafka.topics, settings.kafkaParams
      )
    )
      .transform[Cat] { rdd: RDD[ConsumerRecord[String, String]] =>
        rdd.map(record => Cat(record.key(), record.value().toInt))
      }
  }

  private[this] def processStream(ss: SparkSession, stream: DStream[Cat]): Unit = {
    stream.foreachRDD { rdd =>
      val stats = generateStats(ss.createDataFrame(rdd))
      stats.cache()
      logger.info("show metrics")
      stats.show()
    }
  }

  private[this] def generateStats(cats: DataFrame): DataFrame = {
    cats
      .groupBy(MOOD_COLUMN_NAME)
      .count()
      .sort(desc(COUNT_COLUMN_NAME))
      .groupBy(MOOD_COLUMN_NAME)
      .agg(
        avg(COUNT_COLUMN_NAME).alias("Average"),
        mean(COUNT_COLUMN_NAME).alias("Mean"),
        variance(COUNT_COLUMN_NAME).alias("Variance"),
        skewness(COUNT_COLUMN_NAME).alias("Skewness")
      )
  }
}
