import org.apache.kafka.common.security.auth.SecurityProtocol
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.log4j.{LogManager, Logger}
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql._
import org.apache.spark.sql.types.{DateType, IntegerType, StringType, StructField, StructType}
import org.apache.spark.sql.functions._
import org.apache.spark.streaming.kafka010.ConsumerStrategies.Subscribe
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.streaming.kafka010.{CanCommitOffsets, HasOffsetRanges, KafkaUtils}
import org.apache.spark.streaming.kafka010.LocationStrategies.PreferConsistent
import Launcher._

// to process the KPI asked at large scale, I implement a Spark Streaming DStream' client (the mini-batch approch)
object KPI_processor {

  val  kpiSchema = StructType ( Array(        // theses are the fields of a twitter objet
    StructField("event_date", DateType, true),
    StructField("id", StringType, false),
    StructField("text", StringType, true),
    StructField("lang", StringType, true),
    StructField("userid", StringType, false),
    StructField("name", StringType, false),
    StructField("screenName", StringType, true),
    StructField("location", StringType, true),
    StructField("followersCount", IntegerType, false),
    StructField("retweetCount", IntegerType, false),
    StructField("favoriteCount", IntegerType, false),
    StructField("Zipcode", StringType, true),
    StructField("ZipCodeType", StringType, true),
    StructField("City", StringType, true),
    StructField("State", StringType, true))
  )

  // parameters of the Spark consumer client, needed to connect to Kafka
  private val bootStrapServers : String = kafkaServers
  private val consumerGroupId : String = groupId
  private val consumerReadOrder : String = readOrder
 // private val kerberosName : String = ""
  private val batchDuration  = 300
  private val topics : Array[String] = Array(topic)
  private val mySQLHost = mySQLHost_
  private val mySQLUser = mySQLUser_
  private val mySQLPwd = mySQLPwd_
  private val mySQLDatabase = mySQLDatabase_

  private var logger : Logger = LogManager.getLogger("Log_Console")
  var ss : SparkSession = null
  var spConf : SparkConf = null

  def main(args: Array[String]): Unit = {

    val ssc = getSparkStreamingContext(true, batchDuration )

    val KafkaParam = Map(
      "bootstrap.servers" -> bootStrapServers,
      "group.id"  -> consumerGroupId,
      "auto.offset.reset" -> consumerReadOrder,
      "enable.auto.commit" -> (false: java.lang.Boolean),
      "key.deserializer" -> classOf[StringDeserializer],
      "value.deserializer" -> classOf[StringDeserializer],
     // "sasl.kerberos.service.name" -> KerberosName,
      "security.protocol" -> SecurityProtocol.PLAINTEXT
    )

    try {

      val  kk_consumer = KafkaUtils.createDirectStream[String, String](
        ssc,
        PreferConsistent,
        Subscribe[String, String](topics, KafkaParam )
      )

      kk_consumer.foreachRDD{

        rdd_kafka => {

          val event_kafka = rdd_kafka.map( e => e.value())
          val offsets_kafka = rdd_kafka.asInstanceOf[HasOffsetRanges].offsetRanges

          val ssession : SparkSession = SparkSession.builder.config(rdd_kafka.sparkContext.getConf).enableHiveSupport().getOrCreate()
          import ssession.implicits._

          val events_df = event_kafka.toDF("kafka_jsons")   // every event received from Kafka is first collect in a string-like form, because it is json. So we need to parse it

          if( events_df.count() > 0) {

            val df_parsed = getParsedData(events_df, ssession)
            val df_kpi = getIndicateursComputed(df_parsed, ssession).cache()

            df_kpi.write      // we write to mySQL database (the schema need to be create manually in MySQL before launching this script) for the 3rd requirement
              .format("jdbc")
              .option("url", s"jdbc:mysql://${mySQLHost}?zeroDateTimeBehavior=CONVERT_TO_NULL&serverTimezone=UTC")
              .option("dbtable", mySQLDatabase)
              .option("user", mySQLUser)
              .option("password", mySQLPwd)
              .save()

          }

          kk_consumer.asInstanceOf[CanCommitOffsets].commitAsync(offsets_kafka)   // we commit offsets to Kafka for fault-tolerant purpose
        }

      }

    }  catch {
          case ex: Exception => logger.error(s"the Spark Application encounterd an error ${ex.printStackTrace()}")
    }

    ssc.start()
    ssc.awaitTermination()

  }


  /**
   * Spark Streaming initializer
   * @param env :deploy environnement. if true, then we are executing the client from a localhost
   * @param duree_batch : the SparkStreamingBatchDuration - or micro-batch duration
   * @return : a spark streaming context instance
   */

  def getSparkStreamingContext (env : Boolean = true, duree_batch : Int) : StreamingContext = {
    logger.info("initialisation du contexte Spark Streaming")
    if (env) {
      spConf = new SparkConf().setMaster("local[*]")
        .setAppName("My streaming application")
    } else {
      spConf = new SparkConf().setAppName("My streaming application")
    }
    logger.info(s"the micro-batch duration for data collection is set to : $duree_batch secondes")
    val ssc : StreamingContext = new StreamingContext(spConf, Seconds(duree_batch))

    return ssc

  }

  // since twitter send back tweets in form of json object, we know that in Kafka, the data are stored in a json-format. So we need to parse it first in relational form. That is the purpose of this function
  def getParsedData (kafkaEventsDf : DataFrame, ss : SparkSession) : DataFrame = {

    logger.info("parsing of jsons objects received from kafka in progress...")

    val df_events = kafkaEventsDf.withColumn("kafka_jsons", from_json(col("kafka_jsons"), kpiSchema))
      .select(
        col("kafka_jsons.event_date"),
        col("kafka_jsons.id"),
        col("kafka_jsons.text"),
        col("kafka_jsons.lang"),
        col("kafka_jsons.userid"),
        col("kafka_jsons.name"),
        col("kafka_jsons.screenName"),
        col("kafka_jsons.location"),
        col("kafka_jsons.followersCount"),
        col("kafka_jsons.retweetCount"),
        col("kafka_jsons.favoriteCount"),
        col("kafka_jsons.Zipcode"),
        col("kafka_jsons.ZipCodeType"),
        col("kafka_jsons.City"),
        col("kafka_jsons.State")
      )

    return df_events

  }

  // once we got the right dataframe from the above function, we call process the KPI needed. I prefer to use SQL for this kind of operations, as the SQL is a very portable tool, and it is also easy for every one to see what you did
  def getIndicateursComputed (eventsDf_parsed : DataFrame, ss : SparkSession) : DataFrame = {

    logger.info("KPI processing in progress...")

    eventsDf_parsed.createOrReplaceTempView("events_tweets")

    val df_indicateurs = ss.sql("""
           SELECT t.City,
               count(t.id) OVER (PARTITION BY t.City ORDER BY City) as tweetCount,
               sum(t.bin_retweet) OVER (PARTITION BY t.City ORDER BY City) as retweetCount

           FROM  (
              SELECT date_event, id, City, CASE WHEN retweetCount > 0 THEN 1 ELSE 0 END AS bin_retweet  FROM events_tweets
           ) t
      """
    ).withColumn("date_event", current_timestamp())
      .select(
        col("date_event").alias("events date"),
        col("City").alias("events city"),
        col("tweetCount").alias("Nber of Tweets per city"),
        col("retweetCount").alias("Nber of Retweets per city")
      )

    return df_indicateurs

  }

}
