package amaze.us.mock

import amaze.us.model.Decision
import amaze.us.model.IncomingBabyRequest
import amaze.us.security.LoginRequest
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType

private val objectMapper = ObjectMapper()

fun IncomingBabyRequest.toJson(): String = objectMapper.writeValueAsString(this)
fun Decision.toJson(): String = objectMapper.writeValueAsString(this)
fun LoginRequest.toJson(): String = objectMapper.writeValueAsString(this)

fun jsonEntity(payload: String) = HttpEntity(payload, jsonHeader())
fun jsonHeader() = HttpHeaders().also {
  it.contentType = MediaType.APPLICATION_JSON
}