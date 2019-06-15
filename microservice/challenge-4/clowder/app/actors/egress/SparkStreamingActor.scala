package actors.egress

import actors.egress.AggregationResultsActor.{StatisticsResult, CountResult}
import akka.actor.{Actor, ActorLogging, ActorRef}
import com.typesafe.config.Config
import javax.inject.{Inject, Named}
import org.apache.spark.sql.functions._
import org.apache.spark.sql.streaming.StreamingQuery
import org.apache.spark.sql.types.{DataTypes, StructType}
import org.apache.spark.sql.{DataFrame, SparkSession, functions}

object SparkStreamingActor {
  val moodStruct: StructType = new StructType()
    .add("catName", DataTypes.StringType)
    .add("emotionName", DataTypes.StringType)
    .add("emotion", DataTypes.StringType)
    .add("timestamp", DataTypes.LongType)
}

/**
  * This actor starts a spark session to stream cat moods from kafka.
  * We also start queries from the session and pass results to the AggregationResultsActor
  *
  * @param config           - type safe config
  * @param aggrResultsActor - actor to stream aggregation results to.
  */
class SparkStreamingActor @Inject()(config: Config,
                                    @Named("aggregation-results-actor") aggrResultsActor: ActorRef)
  extends Actor with ActorLogging {

  import SparkStreamingActor._

  private val appName = config.getString("application.name")
  private val kafkaTopic = config.getString("akka.kafka.producer.topic")
  private val bootstrapServers = config.getString("akka.kafka.producer.kafka-clients.bootstrap.servers")

  val spark: SparkSession =
    SparkSession.builder.appName(appName).master("local[*]").getOrCreate()

  val df: DataFrame = readCatMoodStream()

  //Run Queries and Publish Results
  val topMoodsQuery: StreamingQuery = startEmittingTopMoods(df)
  val statisticsQuery: StreamingQuery = startEmittingMoodStatistics(df)

  def readCatMoodStream(): DataFrame = {

    val df: DataFrame = spark
      .readStream
      .format("kafka")
      .option("kafka.bootstrap.servers", bootstrapServers)
      .option("subscribe", kafkaTopic)
      .load()
      .selectExpr("CAST(value AS STRING) as value", "CAST(timestamp AS TIMESTAMP) as timestamp")

    val catMoodsNestedDf: DataFrame =
      df.select(from_json(col("value"), moodStruct).as("catMood"), col("timestamp"))

    val catMoodsFlattenDf: DataFrame =
      catMoodsNestedDf.selectExpr(
        "catMood.catName", "catMood.emotionName", "catMood.emotion", "timestamp")

    catMoodsFlattenDf
  }

  def startEmittingTopMoods(df: DataFrame): StreamingQuery = {

    log.info("Start emitting top moods")

    import spark.implicits._

    val topMoods = df
      .groupBy("emotionName")
      .count
      .orderBy(col("count").desc)
      .writeStream
      .outputMode("complete")
      .foreachBatch { (batchDF: DataFrame, _: Long) =>
        val countResults =
          batchDF.map(row => CountResult(row.getString(0), row.getLong(1))).collect()
        aggrResultsActor ! countResults
      }.start()

    topMoods
  }

  def startEmittingMoodStatistics(df: DataFrame): StreamingQuery = {

    import spark.implicits._

    val windowedDF =
      df.groupBy(
        window(col("timestamp"), "2 minutes", "1 minutes"),
        col("emotionName"))
        .count().orderBy(col("window").desc, col("count").desc)

    windowedDF
      .writeStream
      .outputMode("complete")
      .foreachBatch { (batchDF: DataFrame, _: Long) =>

        val aggrResultsDF =
          batchDF
          .groupBy(col("emotionName"))
          .agg(
            functions.mean("count").as("mean"),
            functions.variance("count").as("variance"))

        val aggrResults =
          aggrResultsDF.map(row =>
            StatisticsResult(
              row.getString(0),
              row.getDouble(1),
              row.getDouble(2))
          ).collect()

        aggrResultsActor ! aggrResults
      }.start()
  }

  override def receive: Receive = {
    case _ =>
      log.info("Message not supported")
  }

  override def postStop(): Unit = {
    if (topMoodsQuery.isActive)
      topMoodsQuery.stop()

    if (statisticsQuery.isActive)
      statisticsQuery.stop()

    if (!spark.sparkContext.isStopped) {
      spark.sparkContext.stop()
      spark.stop()
    }
  }
}
