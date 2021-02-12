package amaze.us.config

import amaze.us.security.SimpleAuthenticationProvider
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter

@EnableWebSecurity
class WebSecurity : WebSecurityConfigurerAdapter() {

  @Autowired
  private lateinit var simpleAuthenticationProvider: SimpleAuthenticationProvider

  @Throws(Exception::class)
  override fun configure(auth: AuthenticationManagerBuilder) {
    auth.authenticationProvider(simpleAuthenticationProvider)
  }

  @Throws(Exception::class)
  override fun configure(http: HttpSecurity) {
    http.csrf().disable()
        .authorizeRequests()
        .antMatchers("/**")
        .permitAll()
  }
}

