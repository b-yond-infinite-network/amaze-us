package amaze.us.db

import amaze.us.config.LOGGER
import com.mongodb.MongoClientSettings
import com.mongodb.ServerAddress
import com.mongodb.client.MongoClients
import com.mongodb.connection.ClusterSettings
import com.mongodb.connection.ConnectionPoolSettings
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.SimpleMongoClientDbFactory
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Component
import java.util.*
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.TimeUnit
import javax.annotation.PostConstruct

@Component
@EnableConfigurationProperties
class MongoTemplateFactory {

  @Value("\${database.mongo.port}")
  private val port = 0

  @Value("\${database.mongo.address}")
  private val hosts: String = "mongo"

  private var dataBasesMap: MutableMap<String, MongoTemplate> = ConcurrentHashMap()

  private lateinit var mongoClientSettings: MongoClientSettings

  @PostConstruct
  fun startConnections() {
    try {
      val serverAddresses: MutableList<ServerAddress> = ArrayList()
      LOGGER.info("Start connections to $hosts")
      hosts.split(",").forEach { ip -> serverAddresses.add(ServerAddress(ip, port)) }
      mongoClientSettings = mongoBuilder()
          .applyToClusterSettings { builder: ClusterSettings.Builder -> builder.hosts(serverAddresses) }
          .applyToConnectionPoolSettings { builder: ConnectionPoolSettings.Builder ->
            builder
                .minSize(10)
                .maxSize(40)
                .maxConnectionIdleTime(10.toLong(), TimeUnit.MINUTES)
          }.build()
      testConnection()
    } catch (e: Exception) {
      LOGGER.error("An Exception has occurred ${e.message}", e)
    }
  }

  private fun mongoBuilder() = MongoClientSettings.builder()

  private fun testConnection() = repeat(10) {
    getDb("requests").find(Query(Criteria.where("name").`is`("testBaby")), BabyRequest::class.java)
  }

  fun getDb(category: String) = dataBasesMap.computeIfAbsent(category) {
    MongoTemplate(SimpleMongoClientDbFactory(MongoClients.create(mongoClientSettings), it))
  }

  @Bean
  fun mongoTemplate() = this.getDb("requests")
}
