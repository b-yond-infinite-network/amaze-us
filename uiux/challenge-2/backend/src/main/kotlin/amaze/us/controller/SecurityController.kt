package amaze.us.controller

import amaze.us.config.LOGGER
import amaze.us.security.AuthenticationService
import amaze.us.security.LoginRequest
import amaze.us.security.LoginResponse
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1")
class SecurityController {

  @Autowired
  private lateinit var authenticationService: AuthenticationService

  @ApiOperation(value = "Login", response = LoginResponse::class)
  @ApiResponses(
      ApiResponse(code = 201, message = "Login successful"),
      ApiResponse(code = 409, message = "Login failed due to incorrect password or username"),
      ApiResponse(code = 500, message = "Failed because of an unexpected error")
  )
  @PostMapping(value = ["/login"])
  internal fun submitBabyRequest(@RequestBody request: LoginRequest) = try {
    LOGGER.info("Processing login for ${request.username}")
    val loginResponse = authenticationService.getAuthenticationToken(request)
    ResponseEntity(loginResponse, if (loginResponse.isOk()) HttpStatus.OK else HttpStatus.BAD_REQUEST)
  } catch (e: Exception) {
    ResponseEntity(LoginResponse(), HttpStatus.INTERNAL_SERVER_ERROR)
  }
}