package example

import java.util.{Properties, Timer, TimerTask}

import org.apache.kafka.clients.producer._
import org.slf4j.LoggerFactory

object CatsProducerApp extends App {
  val catsProducer = new CatsProducerKafka
  catsProducer.publishMoods()
}

trait CatsProducer {
  def publishMoods(): Unit
}

class CatsProducerKafka extends CatsProducer {
  private val log = LoggerFactory.getLogger(this.getClass)

  /* Using a timer to push data at an interval.  The next step would to model incoming cat moods as a
   * real-time data feed. */
  override def publishMoods(): Unit = new Timer().schedule(new TimerTask {
    override def run(): Unit = publishMoodsKafka(KafkaConstants.TOPIC, setupKafkaProducer)
  }, 0, Cats.MOOD_DURATION)

  private def publishMoodsKafka(topic: String, producer: KafkaProducer[String, String]): Unit = {
    Cats.synchronizedMoodSwings(Cats.NB_OF_CATS).map { case (catId, mood) =>
      new ProducerRecord[String, String](topic, catId.toString, mood.toString)
    }.foreach(producer.send)
    log.info(s"Published ${Cats.NB_OF_CATS} new records to Kafka.")
  }

  private def setupKafkaProducer: KafkaProducer[String, String] = {
    val props = new Properties()
    props.put("bootstrap.servers", KafkaConstants.BOOTSTRAP_SERVERS)
    props.put("group.id", KafkaConstants.GROUP_ID)
    props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
    props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer")
    new KafkaProducer[String, String](props)
  }
}