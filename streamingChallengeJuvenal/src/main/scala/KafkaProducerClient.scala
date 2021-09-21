import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord,  _}
import java.util.Properties
import org.apache.kafka.common.security.auth.SecurityProtocol
import org.apache.log4j.{LogManager, Logger}


// I develop an Kafka producer client to get the tweets from the twitter client and stored them in a specific topic
object KafkaProducerClient {

  private var trace_kafka : Logger = LogManager.getLogger("Log_Console")

  def getKafkaProducerParams (KafkaBootStrapServers : String) : Properties = {
    val props : Properties = new Properties()
    props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
    props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer")
    props.put("acks", "all")
    props.put("bootstrap.servers ", KafkaBootStrapServers)
    props.put("security.protocol",  "SASL_PLAINTEXT")

    return props

  }

  /**
   * the kafka producer
   * @param KafkaBootStrapServers : hosts of the brokers Kafka
   * @param topic_name :
   * @param message : messages to publish in the topic @topic_name
   */
  def getProducerKafka (KafkaBootStrapServers : String, topic_name : String, message : String) : Unit = {

    try {

      val producer_Kafka = new KafkaProducer[String, String](getKafkaProducerParams(KafkaBootStrapServers))
      val record_publish = new ProducerRecord[String, String](topic_name, message)

      producer_Kafka.send(record_publish)

    } catch {
      case ex : Exception =>
        trace_kafka.error(s"An erro was encounter in the publishing of the message : ${ex.printStackTrace()}")
    }

  }

  // to scale the producer, we need to specify the key, that is the city, so that each partion will have the tweets of only one city.
  def getProducerKafka_Scale (KafkaBootStrapServers : String, topic_name : String, message : String, message_key : String) : Unit = {

    try {

      val producer_Kafka = new KafkaProducer[String, String](getKafkaProducerParams(KafkaBootStrapServers))
      val record_publish = new ProducerRecord[String, String](topic_name, message_key, message)

      producer_Kafka.send(record_publish)

    } catch {
      case ex : Exception =>
        trace_kafka.error(s"An erro was encounter in the publishing of the message : ${ex.printStackTrace()}")
    }

  }
}
