package producer

import java.util.Properties

import akka.actor.{Actor, ActorLogging, Props}
import com.typesafe.config.ConfigFactory
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerConfig, ProducerRecord}
import org.apache.kafka.common.serialization.{IntegerSerializer, StringSerializer}
import producer.Purrducer.Purrduce


trait KafkaConfig {
    lazy val kafkaConfig = ConfigFactory.load().getConfig("kafka")

    lazy val kafkaHost = kafkaConfig.getString("host")
    lazy val kafkaPort = kafkaConfig.getString("port")
    lazy val kafkaTopic = kafkaConfig.getString("topic")
    lazy val kafkaRetries = kafkaConfig.getInt("retries")
    lazy val kafkaRequestTimeout = kafkaConfig.getInt("requestTimeout")
    lazy val kafkaDeliveryTimeout = kafkaConfig.getInt("deliveryTimeout")
}


trait KafkaPurrducer extends KafkaConfig {
    lazy val props = new Properties()

    props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, s"$kafkaHost:$kafkaPort")
    props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
        classOf[StringSerializer].getCanonicalName)
    props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
        classOf[IntegerSerializer].getCanonicalName)
    props.put(ProducerConfig.RETRIES_CONFIG, kafkaRetries)
    props.put(ProducerConfig.REQUEST_TIMEOUT_MS_CONFIG, kafkaRequestTimeout)
    props.put(ProducerConfig.DELIVERY_TIMEOUT_MS_CONFIG, kafkaDeliveryTimeout)

    lazy val producer = new KafkaProducer[String, Int](props)
}


object Purrducer {
    final case class Purrduce(moodMsg: CatMoodRecord)
    def props = Props(new Purrducer())
}


class CatMoodRecord(val id: Int, val ts: Long, val moodOrdinal: Int)


class Purrducer extends Actor with KafkaPurrducer with ActorLogging {
    override def receive: Receive = {
        case Purrduce(moodRecord) =>
            producer.send(new ProducerRecord[String, Int](kafkaTopic,
                s"${moodRecord.id} ${moodRecord.ts}", moodRecord.moodOrdinal))
    }
}

