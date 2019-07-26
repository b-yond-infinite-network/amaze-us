package consumer

import java.sql.Timestamp
import java.util.Properties

import com.datastax.driver.core.ConsistencyLevel
import com.datastax.spark.connector.streaming._
import com.datastax.spark.connector.AllColumns
import com.datastax.spark.connector.writer.WriteConf
import com.typesafe.config.ConfigFactory
import org.apache.kafka.clients.consumer.{ConsumerConfig, KafkaConsumer}
import org.apache.kafka.common.serialization.{IntegerDeserializer, StringDeserializer}
import org.apache.spark.streaming.kafka010.{ConsumerStrategies, KafkaUtils, LocationStrategies}
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.log4j.{Level, Logger}


trait Config {
    lazy val config = ConfigFactory.load()
}

trait KafkaConfig extends Config {
    lazy val kafkaConfig = config.getConfig("kafka")

    lazy val kafkaHost = kafkaConfig.getString("host")
    lazy val kafkaPort = kafkaConfig.getString("port")
    lazy val kafkaGroup = kafkaConfig.getString("groupId")
    lazy val kafkaTopic = kafkaConfig.getString("topic")
}


trait KafkaConzumer extends KafkaConfig {
    lazy val props = new Properties()
    val location = s"$kafkaHost:$kafkaPort"

    props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, location)
    props.put(ConsumerConfig.GROUP_ID_CONFIG, kafkaGroup)
    props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
        classOf[StringDeserializer].getCanonicalName)
    props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
        classOf[IntegerDeserializer].getCanonicalName)

    lazy val consumer = new KafkaConsumer(props)

    def consume(): Unit

    def kafkaConfigAsMap: Map[String, Object] = {
        Map[String, Object](
            ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG -> location,
            ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG -> classOf[StringDeserializer],
            ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG -> classOf[IntegerDeserializer],
            ConsumerConfig.GROUP_ID_CONFIG -> kafkaGroup)
    }
}


trait DbConfig extends Config {
    lazy val dbConfig = config.getConfig("cassandra")

    lazy val dbHost = dbConfig.getString("host")
    lazy val dbUser = dbConfig.getString("username")
    lazy val dbPassword = dbConfig.getString("password")
    lazy val keySpace = dbConfig.getString("keyspace")
    lazy val tableName = dbConfig.getString("table")
}


class CatMoodRecord(val catId: Int, val ts: Timestamp, val moodOrdinal: Int)


object Conzumer {
    def tokenizeKey(k: String): (Int, Timestamp) = {
        val tokens = k.split(' ')

        (tokens(0).toInt, new Timestamp(tokens(1).toLong)) // TODO validation
    }
}


class Conzumer extends KafkaConzumer with DbConfig {
    import Conzumer._

    override def consume(): Unit = {
        val conf = new SparkConf(true).set("spark.cassandra.connection.host", dbHost)
            .set("spark.cassandra.auth.username", dbUser)
            .set("spark.cassandra.auth.password", dbPassword)
        val context = new SparkContext("local", "catz", conf)
        context.setLogLevel(Level.ERROR.toString)
        val streamingContext = new StreamingContext(context, Seconds(1))

        val consumerStrategy = ConsumerStrategies.Subscribe[String, Integer](
            List(kafkaTopic), kafkaConfigAsMap)
        val kafkaStream = KafkaUtils.createDirectStream[String, Integer](
            streamingContext, LocationStrategies.PreferBrokers, consumerStrategy)

        val cassandraWriteConf = WriteConf.fromSparkConf(conf).copy(
            consistencyLevel = ConsistencyLevel.ONE)

        kafkaStream.map(consumerRecord => {
            val (id, ts) = tokenizeKey(consumerRecord.key())
            (id, ts, consumerRecord.value.toInt)
        }).saveToCassandra(keySpace, tableName, AllColumns, cassandraWriteConf)

        streamingContext.start()
        streamingContext.awaitTermination()
    }
}
