package amaze.us.service

import amaze.us.db.toBabyRequest
import amaze.us.db.toBabyUpdate
import amaze.us.model.Decision
import amaze.us.model.Decision.Companion.APPROVED
import amaze.us.model.Decision.Companion.DENIED
import amaze.us.model.IncomingBabyRequest
import amaze.us.model.ListOfBabyRequest
import amaze.us.model.PopulationAmount
import amaze.us.service.BabyRequestService.Companion.approved
import amaze.us.service.BabyRequestService.Companion.new
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class PopulationService {

  @Autowired
  private lateinit var babyRequestService: BabyRequestService

  companion object {
    const val ILLEGAL_CHARS = "0123456789+-*/\\|][{};:\"?><,!@#$%^&"
  }

  private var population = 2000

  fun count() = PopulationAmount((population + babyRequestService.getRequests(approved).size).toString())

  fun pendingBabyRequests(): ListOfBabyRequest = ListOfBabyRequest(babyRequestService.getRequests(new))

  fun processedBabyRequests(): ListOfBabyRequest = ListOfBabyRequest(babyRequestService.getRequests(approved).sortedByDescending { it.timestamp })

  fun processNewBabyRequest(request: IncomingBabyRequest): Boolean {
    val isGoodName = request.name.none { it in ILLEGAL_CHARS } && request.name.isNotBlank()
    if (isGoodName) babyRequestService.createRequest(request.toBabyRequest)
    return isGoodName
  }

  fun processBabyRequestUpdate(id: String, decision: Decision) = when (decision.status.toLowerCase()) {
    APPROVED, DENIED -> babyRequestService.updateRequest(id, decision.toBabyUpdate)
    else -> false
  }
}