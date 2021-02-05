package amaze.us.controller

import amaze.us.config.LOGGER
import amaze.us.model.CurrentBabyRequests
import amaze.us.model.Decision
import amaze.us.model.IncomingBabyRequest
import amaze.us.model.PopulationAmount
import amaze.us.service.ColonyHandlerService
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/v1")
class ColonyController {

  @Autowired
  private lateinit var colonyHandlerService: ColonyHandlerService

  @ApiOperation(value = "Get the current amount of population in the colony", response = PopulationAmount::class)
  @GetMapping(value = ["/population"], produces = ["application/json"])
  fun populationAmount() = try {
    LOGGER.info("Retrieving population")
    ResponseEntity(colonyHandlerService.populationAmount(), HttpStatus.OK)
  } catch (e: Exception) {
    ResponseEntity(PopulationAmount(), HttpStatus.INTERNAL_SERVER_ERROR)
  }

  @ApiOperation(value = "Get the list of pending baby requests", response = CurrentBabyRequests::class)
  @GetMapping(value = ["/baby/request"], produces = ["application/json"])
  fun babyPendingRequests() = try {
    LOGGER.info("Retrieving baby requests")
    ResponseEntity(colonyHandlerService.babyRequests(), HttpStatus.OK)
  } catch (e: Exception) {
    ResponseEntity(CurrentBabyRequests(), HttpStatus.INTERNAL_SERVER_ERROR)
  }

  @ApiOperation(value = "Submit a baby request", response = IncomingBabyRequest::class)
  @ApiResponses(
      ApiResponse(code = 201, message = "Request have been accepted and created"),
      ApiResponse(code = 409, message = "Request have been denied due to bad character in baby's name"),
      ApiResponse(code = 500, message = "Failed because of an unexpected error")
  )
  @PostMapping(value = ["/baby/request"])
  internal fun submitBabyRequest(@RequestBody request: IncomingBabyRequest) = try {
    LOGGER.info("Processing request $request")
    ResponseEntity("", if (colonyHandlerService.addBabyRequests(request)) HttpStatus.CREATED else HttpStatus.BAD_REQUEST)
  } catch (e: Exception) {
    ResponseEntity("", HttpStatus.INTERNAL_SERVER_ERROR)
  }

  @ApiOperation(value = "Decide on a baby request", response = Decision::class)
  @ApiResponses(
      ApiResponse(code = 200, message = "Request have been correctly processed"),
      ApiResponse(code = 409, message = "Decision or ID was not recognized"),
      ApiResponse(code = 500, message = "Failed because of an unexpected error")
  )
  @PutMapping(value = ["/baby/request/{id}"])
  internal fun decisionOnBabyRequest(@PathVariable id: String, @RequestBody decision: Decision) = try {
    LOGGER.info("Processing decision $decision for $id")
    ResponseEntity("", if (colonyHandlerService.processDecision(id, decision)) HttpStatus.OK else HttpStatus.BAD_REQUEST)
  } catch (e: Exception) {
    ResponseEntity("", HttpStatus.INTERNAL_SERVER_ERROR)
  }
}