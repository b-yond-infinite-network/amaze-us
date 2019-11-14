package example

import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.log4j.{Level, Logger}
import org.apache.spark._
import org.apache.spark.streaming.dstream.InputDStream
import org.apache.spark.streaming.kafka010.ConsumerStrategies.Subscribe
import org.apache.spark.streaming.kafka010.LocationStrategies.PreferConsistent
import org.apache.spark.streaming.kafka010._
import org.apache.spark.streaming.{Seconds, StreamingContext}

object CatsConsumerApp extends App {
  val catsConsumer = new CatsConsumerKafka(new InMemoryStore)
  val (ssc, stream) = catsConsumer.setupSparkStreaming()
  catsConsumer.consumeKafkaMessages(stream)
  ssc.start()
  ssc.awaitTermination()
  catsConsumer.generateStatistics()
}

trait CatsConsumer {
  def generateStatistics(): Unit
}


class CatsConsumerKafka(storage: DataStore = new InMemoryStore) extends CatsConsumer {

  /* There doesn't seem to be a sensible way to get the mean/median/variance of cat moods unless we have some way of
   * ordering them, so I am simply printing out the moods in order of count. */
  override def generateStatistics(): Unit = storage match {
    case s: InMemoryStore => s.logToConsole()
    case _ => () // This should be done in a separate app.
  }

  /* For simplicity, I am accumulating the new data in a mutable map. The next step would be to store this data in
   * some distributed data store. The analytics would be displayed on a separate reactive app connecting to that data
   * store. */
  def consumeKafkaMessages(stream: InputDStream[ConsumerRecord[String, String]]): Unit = {
    // MapReduce
    val moodsPerKafkaMessage = stream.map(data => (data.value, 1))
    val moodCount = moodsPerKafkaMessage.reduceByKey(_ + _)
    moodCount.print
    moodCount.foreachRDD(rdd => storage.store(rdd.collectAsMap()))
  }

  def setupSparkStreaming(): (StreamingContext, InputDStream[ConsumerRecord[String, String]]) = {
    val sparkConf = new SparkConf().setAppName("CatsMoodAnalyzer").setMaster("local[*]")

    /* The producer will only produce a new batch of message every 27 seconds,
     * but in a real use case, the messages would come from different sources and would not
     * necessarily be synchronized. */
    val ssc = new StreamingContext(sparkConf, Seconds(2))

    /* Disable spark info level logs as too many lines are logged. */
    Logger.getLogger("org").setLevel(Level.OFF)

    val kafkaParams = Map[String, Object](
      "bootstrap.servers" -> KafkaConstants.BOOTSTRAP_SERVERS,
      "group.id" -> KafkaConstants.GROUP_ID,
      "key.deserializer" -> classOf[StringDeserializer],
      "value.deserializer" -> classOf[StringDeserializer],
      "auto.offset.reset" -> "latest",
      "enable.auto.commit" -> (false: java.lang.Boolean)
    )
    val stream = KafkaUtils.createDirectStream[String, String](
      ssc,
      PreferConsistent,
      Subscribe[String, String](Seq(KafkaConstants.TOPIC), kafkaParams)
    )
    (ssc, stream)
  }
}