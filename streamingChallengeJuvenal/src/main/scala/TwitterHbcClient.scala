import com.twitter.hbc.httpclient.auth._
import com.twitter.hbc.core.{Client, Constants}
import com.twitter.hbc.core.endpoint.{StatusesFilterEndpoint, Location}
import java.util.concurrent.{BlockingQueue, LinkedBlockingQueue, TimeUnit}
import com.twitter.hbc.ClientBuilder
import com.twitter.hbc.core.processor.StringDelimitedProcessor

import scala.collection.JavaConverters._
import org.apache.log4j.{LogManager, Logger}
import KafkaProducerClient._


// I used twitter Hosebird Client API to develop a specific client that will takes data from a specific place, and stored them in a Kafka topic
object TwitterHbcClient {

  private var trace_client_streaming: Logger = LogManager.getLogger("Log_Console")


  /**
   * The Hosebird client
   *
   * @param CONSUMER_KEY          : the OAuth consumer key of the twitter account
   * @param CONSUMER_SECRET       : the OAuth consumer secret of the twitter account
   * @param ACCESS_TOKEN          : the OAuth access token of the twitter account
   * @param TOKEN_SECRET          : the OAuth token secret  of the twitter account
   * @param town_list             : the specific place from where we collect the tweets
   * @param kafkaBootStrapServers : Kafka brokers IP adresses
   * @param topic                 : topic to use in Kafka
   */

  def ProducerTwitterKafkaHbc(CONSUMER_KEY: String, CONSUMER_SECRET: String, ACCESS_TOKEN: String, TOKEN_SECRET: String, town_list: Location,
                              kafkaBootStrapServers: String, topic: String): Unit = {

    val queue: BlockingQueue[String] = new LinkedBlockingQueue[String](100000)

    val auth: Authentication = new OAuth1(CONSUMER_KEY, CONSUMER_SECRET, ACCESS_TOKEN, TOKEN_SECRET)

    val endp: StatusesFilterEndpoint = new StatusesFilterEndpoint()
    endp.locations(List(town_list).asJava)

    val constructeur_hbc: ClientBuilder = new ClientBuilder()
      .hosts(Constants.STREAM_HOST)
      .authentication(auth)
      .gzipEnabled(true)
      .endpoint(endp)
      .processor(new StringDelimitedProcessor(queue)
      )

    val client_hbc: Client = constructeur_hbc.build()

    try {
      client_hbc.connect()

      while (!client_hbc.isDone) {
        val tweets: String = queue.poll(300, TimeUnit.SECONDS)

        getProducerKafka(kafkaBootStrapServers, topic, tweets)     // integration with our Kafka producer

      }
    } catch {

      case ex: InterruptedException => trace_client_streaming.error("The twitter HBC client was interrupted due to an error :" + ex.printStackTrace())

    } finally {
      client_hbc.stop()
    }
  }

}