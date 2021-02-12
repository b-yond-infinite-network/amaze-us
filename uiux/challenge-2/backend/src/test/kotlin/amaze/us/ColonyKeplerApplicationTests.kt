package amaze.us

import amaze.us.mock.jsonEntity
import amaze.us.mock.toJson
import amaze.us.model.Decision
import amaze.us.model.IncomingBabyRequest
import amaze.us.model.ListOfBabyRequest
import amaze.us.model.PopulationAmount
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.context.support.TestPropertySourceUtils
import org.testcontainers.containers.MongoDBContainer
import java.net.URI

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(
    initializers = [
      ColonyKeplerApplicationTests.Companion.MongoInitializer::class
    ]
)
@ExtendWith(SpringExtension::class)
internal class ColonyKeplerApplicationTests {

  var testRestTemplate = TestRestTemplate()

  @LocalServerPort
  var applicationPort: Int = 0

  companion object {
    val mongoDBContainer = MongoDBContainer("mongo:4.0.10")

    @BeforeAll
    @JvmStatic
    fun setup() {
      mongoDBContainer.start()
    }

    @AfterAll
    @JvmStatic
    fun teardown() {
      mongoDBContainer.stop()
    }

    class MongoInitializer : ApplicationContextInitializer<ConfigurableApplicationContext> {
      override fun initialize(context: ConfigurableApplicationContext) {
        val port = mongoDBContainer.firstMappedPort
        mongoDBContainer.portBindings = listOf("$port:$port")
        TestPropertySourceUtils.addInlinedPropertiesToEnvironment(context, "database.mongo.address=localhost", "database.mongo.port=$port", "database.mongo.user=", "database.mongo.password=")
      }
    }
  }

  @Test
  fun healthTest() {
    val result: ResponseEntity<Void> = testRestTemplate.exchange(
        URI(applicationUrl() + "/health"),
        HttpMethod.GET,
        HttpEntity(""),
        Void::class.java)

    Assertions.assertEquals(HttpStatus.OK, result.statusCode)
  }

  @Test
  fun getAmountOfPopulationTest() {
    val result = testRestTemplate.exchange(
        URI(applicationUrl() + "/v1/population"),
        HttpMethod.GET,
        HttpEntity(""),
        PopulationAmount::class.java)

    Assertions.assertEquals(HttpStatus.OK, result.statusCode)
    Assertions.assertEquals(PopulationAmount("2000"), result.body)
  }

  @Test
  fun getBabyRequestsTest() {
    val result = testRestTemplate.exchange(
        URI(applicationUrl() + "/v1/baby/request"),
        HttpMethod.GET,
        HttpEntity(""),
        ListOfBabyRequest::class.java)

    Assertions.assertEquals(HttpStatus.OK, result.statusCode)
    Assertions.assertEquals(ListOfBabyRequest(), result.body)
  }

  @Test
  fun postBabyRequestTest() {
    val result = addNewBabyRequest("Baby Wan Kenobi")

    Assertions.assertEquals(HttpStatus.CREATED, result.statusCode)
  }

  @Test
  fun postBabyRequestWrongNameTest() {
    val result = addNewBabyRequest("Tr0l0l0 B@by")

    Assertions.assertEquals(HttpStatus.BAD_REQUEST, result.statusCode)
  }

  @Test
  fun postBabyRequestBlankNameTest() {
    val result = addNewBabyRequest("   ")

    Assertions.assertEquals(HttpStatus.BAD_REQUEST, result.statusCode)
  }

  @Test
  fun babyRequestApprovalTest() {
    addNewBabyRequest("New Baby")
    val lastBabyRequest = testRestTemplate.exchange(
        URI(applicationUrl() + "/v1/baby/request"),
        HttpMethod.GET,
        HttpEntity(""),
        ListOfBabyRequest::class.java).body!!.requests.last()

    val result = testRestTemplate.exchange(
        URI(applicationUrl() + "/v1/baby/request/${lastBabyRequest.id}"),
        HttpMethod.PUT,
        jsonEntity(Decision("Approved", "reviewer").toJson()),
        Void::class.java)

    val decided = testRestTemplate.exchange(
        URI(applicationUrl() + "/v1/baby/request/audit"),
        HttpMethod.GET,
        HttpEntity(""),
        ListOfBabyRequest::class.java)

    Assertions.assertEquals(HttpStatus.OK, result.statusCode)
    Assertions.assertEquals(HttpStatus.OK, decided.statusCode)
    Assertions.assertEquals("reviewer", decided.body!!.requests.first().reviewer)
    Assertions.assertEquals(lastBabyRequest.id, decided.body!!.requests.first().id)
  }

  @Test
  fun babyRequestDeniedTest() {
    addNewBabyRequest("New Baby")
    val lastBabyRequest = testRestTemplate.exchange(
        URI(applicationUrl() + "/v1/baby/request"),
        HttpMethod.GET,
        HttpEntity(""),
        ListOfBabyRequest::class.java).body!!.requests.last()

    val result = testRestTemplate.exchange(
        URI(applicationUrl() + "/v1/baby/request/${lastBabyRequest.id}"),
        HttpMethod.PUT,
        jsonEntity(Decision("Denied", "").toJson()),
        Void::class.java)

    Assertions.assertEquals(HttpStatus.OK, result.statusCode)
  }

  @Test
  fun babyRequestUnknownDecisionTest() {
    addNewBabyRequest("New Baby")
    val lastBabyRequest = testRestTemplate.exchange(
        URI(applicationUrl() + "/v1/baby/request"),
        HttpMethod.GET,
        HttpEntity(""),
        ListOfBabyRequest::class.java).body!!.requests.last()

    val result = testRestTemplate.exchange(
        URI(applicationUrl() + "/v1/baby/request/${lastBabyRequest.id}"),
        HttpMethod.PUT,
        jsonEntity(Decision("Dunno", "").toJson()),
        Void::class.java)

    Assertions.assertEquals(HttpStatus.BAD_REQUEST, result.statusCode)
  }

  @Test
  fun babyRequestApprovalWrongIDTest() {
    val result = testRestTemplate.exchange(
        URI(applicationUrl() + "/v1/baby/request/RandomID"),
        HttpMethod.PUT,
        jsonEntity(Decision("Approved", "").toJson()),
        Void::class.java)

    Assertions.assertEquals(HttpStatus.BAD_REQUEST, result.statusCode)
  }

  private fun applicationUrl() = "http://localhost:$applicationPort"

  private fun addNewBabyRequest(name: String): ResponseEntity<Void> {
    return testRestTemplate.exchange(
        URI(applicationUrl() + "/v1/baby/request"),
        HttpMethod.POST,
        jsonEntity(IncomingBabyRequest(name, "").toJson()),
        Void::class.java)
  }
}