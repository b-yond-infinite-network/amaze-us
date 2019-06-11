package actors.ingress

import actors.ingress.MoodPublisher.PublishedMood
import akka.actor.{Actor, ActorLogging}
import akka.kafka.{ProducerMessage, ProducerSettings}
import com.typesafe.config.Config
import javax.inject.Inject
import org.apache.kafka.clients.producer.{Producer, ProducerRecord}
import org.apache.kafka.common.serialization.StringSerializer
import play.api.libs.json.{JsObject, Json, Writes}

object MoodPublisher {
  case class PublishedMood(emotionName: String, emotion: String, catName: String, timestamp: Long)

  implicit val moodWrites = new Writes[PublishedMood] {
    def writes(mood: PublishedMood): JsObject = Json.obj(
      "catName" -> mood.catName,
      "emotionName" -> mood.emotionName,
      "emotion" -> mood.emotion,
      "timestamp" -> mood.timestamp
    )
  }

  trait Factory {
    def apply(): Actor
  }
}

/**
  * An actor which sends cat moods to kafka
  * @param config - typesafe config
  */
class MoodPublisher @Inject() (config: Config) extends Actor with ActorLogging {

  val producerConf: Config = config.getConfig("akka.kafka.producer")
  val topic: String = producerConf.getString("topic")

  val producerSettings: ProducerSettings[String, String] =
    ProducerSettings(
      producerConf,
      new StringSerializer,
      new StringSerializer
    ).withBootstrapServers(producerConf.getString("kafka-clients.bootstrap.servers"))
  val kafkaProducer: Producer[String, String] = producerSettings.createKafkaProducer()

  override def receive: Receive = {
    case mood: PublishedMood =>
      val message = Json.toJson(mood)
      val record = new ProducerRecord[String, String](topic, message.toString())
      kafkaProducer.send(record)
  }

  def createMessage[KeyType, ValueType, PassThroughType](
                                                          value: ValueType,
                                                          passThrough: PassThroughType
                                                        ): ProducerMessage.Envelope[KeyType, ValueType, PassThroughType] = {
    val single: ProducerMessage.Envelope[KeyType, ValueType, PassThroughType] =
      ProducerMessage.single(
        new ProducerRecord(producerConf.getString("topic"), value),
        passThrough
      )
    single
  }
}
