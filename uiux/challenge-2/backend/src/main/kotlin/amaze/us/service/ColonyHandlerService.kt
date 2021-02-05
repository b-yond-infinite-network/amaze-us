package amaze.us.service

import amaze.us.model.CurrentBabyRequests
import amaze.us.model.Decision
import amaze.us.model.Decision.Companion.APPROVED
import amaze.us.model.Decision.Companion.DENIED
import amaze.us.model.IncomingBabyRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ColonyHandlerService {

  @Autowired
  private lateinit var populationService: PopulationService

  fun populationAmount() = populationService.count()
  fun babyRequests(): CurrentBabyRequests = populationService.pendingBabyRequests()
  fun addBabyRequests(request: IncomingBabyRequest) = populationService.processNewBabyRequest(request)
  fun processDecision(id: String, decision: Decision) = when (decision.status.toLowerCase()) {
    APPROVED -> populationService.approveRequest(id)
    DENIED -> populationService.denyRequest(id)
    else -> false
  }
}
