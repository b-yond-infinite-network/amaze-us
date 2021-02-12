package amaze.us.security

import amaze.us.mock.jsonEntity
import amaze.us.mock.toJson
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.net.URI

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension::class)
internal class ColonyKeplerApplicationTests {

  var testRestTemplate = TestRestTemplate()

  @LocalServerPort
  var applicationPort: Int = 0

  private fun applicationUrl() = "http://localhost:$applicationPort"

  @Test
  fun loginSuccessfulTest() {
    val response = testRestTemplate.exchange(
        URI(applicationUrl() + "/v1/login"),
        HttpMethod.POST,
        jsonEntity(LoginRequest("user", "pass").toJson()),
        LoginResponse::class.java)

    Assertions.assertEquals(HttpStatus.OK, response.statusCode)
  }

  @Test
  fun loginBadCredentialsTest() {
    val response = testRestTemplate.exchange(
        URI(applicationUrl() + "/v1/login"),
        HttpMethod.POST,
        jsonEntity(LoginRequest("user", " ").toJson()),
        LoginResponse::class.java)

    Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.statusCode)
    Assertions.assertEquals("", response.body!!.token)
  }
}