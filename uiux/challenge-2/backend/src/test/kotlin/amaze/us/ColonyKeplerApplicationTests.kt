package amaze.us

import amaze.us.mock.jsonEntity
import amaze.us.mock.toJson
import amaze.us.model.CurrentBabyRequests
import amaze.us.model.Decision
import amaze.us.model.IncomingBabyRequest
import amaze.us.model.PopulationAmount
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.net.URI

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension::class)
internal class ColonyKeplerApplicationTests {

  var testRestTemplate = TestRestTemplate()

  @LocalServerPort
  var applicationPort: Int = 0

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
        CurrentBabyRequests::class.java)

    Assertions.assertEquals(HttpStatus.OK, result.statusCode)
    Assertions.assertEquals(CurrentBabyRequests(), result.body)
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
        CurrentBabyRequests::class.java).body!!.requests.last()

    val result = testRestTemplate.exchange(
        URI(applicationUrl() + "/v1/baby/request/${lastBabyRequest.id}"),
        HttpMethod.PUT,
        jsonEntity(Decision("Approved").toJson()),
        Void::class.java)

    Assertions.assertEquals(HttpStatus.OK, result.statusCode)
  }

  @Test
  fun babyRequestDeniedTest() {
    addNewBabyRequest("New Baby")
    val lastBabyRequest = testRestTemplate.exchange(
        URI(applicationUrl() + "/v1/baby/request"),
        HttpMethod.GET,
        HttpEntity(""),
        CurrentBabyRequests::class.java).body!!.requests.last()

    val result = testRestTemplate.exchange(
        URI(applicationUrl() + "/v1/baby/request/${lastBabyRequest.id}"),
        HttpMethod.PUT,
        jsonEntity(Decision("Denied").toJson()),
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
        CurrentBabyRequests::class.java).body!!.requests.last()

    val result = testRestTemplate.exchange(
        URI(applicationUrl() + "/v1/baby/request/${lastBabyRequest.id}"),
        HttpMethod.PUT,
        jsonEntity(Decision("Dunno").toJson()),
        Void::class.java)

    Assertions.assertEquals(HttpStatus.BAD_REQUEST, result.statusCode)
  }


  @Test
  fun babyRequestApprovalWrongIDTest() {
    val result = testRestTemplate.exchange(
        URI(applicationUrl() + "/v1/baby/request/RandomID"),
        HttpMethod.PUT,
        jsonEntity(Decision("Approved").toJson()),
        Void::class.java)

    Assertions.assertEquals(HttpStatus.BAD_REQUEST, result.statusCode)
  }

  private fun applicationUrl() = "http://localhost:$applicationPort"

  private fun addNewBabyRequest(name: String): ResponseEntity<Void> {
    return testRestTemplate.exchange(
        URI(applicationUrl() + "/v1/baby/request"),
        HttpMethod.POST,
        jsonEntity(IncomingBabyRequest(name).toJson()),
        Void::class.java)
  }
}