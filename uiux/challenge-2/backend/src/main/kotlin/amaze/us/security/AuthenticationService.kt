package amaze.us.security

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.util.*

@Service
class AuthenticationService {

  @Value("\${auth.jwt.key}")
  private val key = "jwt key"

  @Value("\${auth.jwt.validity}")
  private val validity = 0

  fun getAuthenticationToken(loginRequest: LoginRequest) =
      LoginResponse(if (isValidUser(loginRequest)) generateJwtToken(loginRequest) else "")

  private fun generateJwtToken(loginRequest: LoginRequest): String = Jwts.builder()
      .setSubject(loginRequest.username)
      .setIssuedAt(Date(System.currentTimeMillis()))
      .setExpiration(Date(System.currentTimeMillis() + validity))
      .signWith(SignatureAlgorithm.HS512, key).compact()

  private fun isValidUser(loginRequest: LoginRequest) = loginRequest.username.isNotBlank() && loginRequest.password.isNotBlank()
}

data class LoginRequest(val username: String, val password: String)
data class LoginResponse(val token: String = "") {
  fun isOk() = token.isNotBlank()
}