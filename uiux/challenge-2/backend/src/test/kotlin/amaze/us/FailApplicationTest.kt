package amaze.us

import amaze.us.mock.jsonEntity
import amaze.us.mock.toJson
import amaze.us.model.Decision
import amaze.us.model.IncomingBabyRequest
import amaze.us.model.ListOfBabyRequest
import amaze.us.model.PopulationAmount
import amaze.us.security.AuthenticationService
import amaze.us.security.LoginRequest
import amaze.us.security.LoginResponse
import amaze.us.service.ColonyHandlerService
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.context.annotation.Bean
import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.net.URI

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension::class)
internal class FailApplicationTest {

  var testRestTemplate = TestRestTemplate()

  @LocalServerPort
  var applicationPort: Int = 0

  @TestConfiguration
  internal class KeplerTestConfig {

    @Bean
    fun colonyHandlerService(): ColonyHandlerService {
      val colonyService = mockk<ColonyHandlerService>()
      every { colonyService.populationAmount() } throws RuntimeException()
      every { colonyService.babyRequests() } throws RuntimeException()
      every { colonyService.addBabyRequests(any()) } throws RuntimeException()
      every { colonyService.processDecision(any(), any()) } throws RuntimeException()
      every { colonyService.processedRequests() } throws RuntimeException()
      return colonyService
    }

    @Bean
    fun authenticationService(): AuthenticationService {
      val authenticationService = mockk<AuthenticationService>()
      every { authenticationService.getAuthenticationToken(any()) } throws RuntimeException()
      return authenticationService
    }
  }

  @Test
  fun failToGetPopulationTest() {
    val result = testRestTemplate.exchange(
        URI(applicationUrl() + "/v1/population"),
        HttpMethod.GET,
        HttpEntity(""),
        PopulationAmount::class.java)

    Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, result.statusCode)
    Assertions.assertEquals(PopulationAmount(), result.body)
  }

  @Test
  fun failToGetBabyRequestsTest() {
    val result = testRestTemplate.exchange(
        URI(applicationUrl() + "/v1/baby/request"),
        HttpMethod.GET,
        HttpEntity(""),
        ListOfBabyRequest::class.java)

    Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, result.statusCode)
    Assertions.assertEquals(ListOfBabyRequest(), result.body)
  }

  @Test
  fun failToPostBabyRequestsTest() {
    val result = testRestTemplate.exchange(
        URI(applicationUrl() + "/v1/baby/request"),
        HttpMethod.POST,
        jsonEntity(IncomingBabyRequest().toJson()),
        Void::class.java)

    Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, result.statusCode)
  }

  @Test
  fun babyRequestApprovalTest() {
    val result = testRestTemplate.exchange(
        URI(applicationUrl() + "/v1/baby/request/babyRequestId"),
        HttpMethod.PUT,
        jsonEntity(Decision("Approved", "").toJson()),
        Void::class.java)

    Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, result.statusCode)
  }

  @Test
  fun failToGetAuditBabyRequestsTest() {
    val result = testRestTemplate.exchange(
        URI(applicationUrl() + "/v1/baby/request/audit"),
        HttpMethod.GET,
        HttpEntity(""),
        ListOfBabyRequest::class.java)

    Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, result.statusCode)
    Assertions.assertEquals(ListOfBabyRequest(), result.body)
  }

  @Test
  fun failToLoginTest() {
    val response = testRestTemplate.exchange(
        URI(applicationUrl() + "/v1/login"),
        HttpMethod.POST,
        jsonEntity(LoginRequest("user", " ").toJson()),
        LoginResponse::class.java)

    Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.statusCode)
    Assertions.assertEquals("", response.body!!.token)
  }

  private fun applicationUrl() = "http://localhost:$applicationPort"
}