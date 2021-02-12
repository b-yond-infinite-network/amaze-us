package amaze.us.security

import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.stereotype.Component

@Component
class SimpleAuthenticationProvider : AuthenticationProvider {
  @Throws(AuthenticationException::class)
  override fun authenticate(authentication: Authentication) =
      UsernamePasswordAuthenticationToken(authentication.name, authentication.credentials.toString(), ArrayList())

  override fun supports(authentication: Class<*>): Boolean {
    return authentication == UsernamePasswordAuthenticationToken::class.java
  }
}