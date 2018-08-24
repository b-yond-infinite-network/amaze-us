package com.byond.challenge4.configuration

import java.util.Properties

import org.apache.kafka.clients.producer.{KafkaProducer, ProducerConfig}
import org.apache.kafka.common.serialization.StringDeserializer

import scala.concurrent.duration.Duration

case class Kafka(brokers: String, topics: List[String], groupId: String, autoOffsetReset: String, batchDuration: Duration,
                 keySerializer: String, valueSerializer: String)

case class Akka(intervalDuration: Duration, catsToSense: Long)

case class Settings(appName: String, kafka: Kafka, akka: Akka) {

  def kafkaParams: Map[String, Object] = Map[String, Object](
      "bootstrap.servers"  ->  kafka.brokers,
      "key.deserializer"   -> classOf[StringDeserializer],
      "value.deserializer" -> classOf[StringDeserializer],
      "group.id"           -> kafka.groupId,
      "auto.offset.reset"  -> kafka.autoOffsetReset
    )

  def kafkaProps: Properties = {
    val properties = new Properties

    properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafka.brokers)
    properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, kafka.keySerializer)
    properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, kafka.valueSerializer)
    properties
  }

}

case class SensorSettings(producer: KafkaProducer[String, String], topic: String, numOfCatsToSense: Long, intervalToSense: Duration)
