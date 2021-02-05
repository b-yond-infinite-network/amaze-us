package amaze.us.service

import amaze.us.model.CurrentBabyRequests
import amaze.us.model.IncomingBabyRequest
import amaze.us.model.PopulationAmount
import org.springframework.stereotype.Service

@Service
class PopulationService {

  companion object {
    const val ILLEGAL_CHARS = "0123456789+-*/\\|][{};:\"?><,!@#$%^&"
  }

  private var population = 2000
  private var babyRequests = CurrentBabyRequests()

  fun count() = PopulationAmount(population.toString())
  fun pendingBabyRequests(): CurrentBabyRequests = babyRequests
  fun processNewBabyRequest(request: IncomingBabyRequest): Boolean {
    val isGoodName = request.name.none { it in ILLEGAL_CHARS } && request.name.isNotBlank()
    if (isGoodName) babyRequests.add(request)
    return isGoodName
  }
  private fun removeRequest(id: String) = babyRequests.requests.removeIf{ it.id == id }
  fun approveRequest(id: String) = removeRequest(id).also { if (it) population += 1 }
  fun denyRequest(id: String) = removeRequest(id)
}