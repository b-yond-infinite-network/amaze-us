package amaze.us.model

import amaze.us.model.Decision.Companion.DENIED
import amaze.us.model.Decision.Companion.NEW
import java.util.*

data class PopulationAmount(val amount: String) {
  constructor() : this("N/A")
}

data class IncomingBabyRequest(val name: String) {
  constructor() : this("N/A")
}

data class ProcessingBabyRequest(val name: String, val id: String = UUID.randomUUID().toString(), val status: String = NEW) {
  constructor() : this("N/A", "null-${UUID.randomUUID()}", DENIED)
}

data class Decision(val status: String) {
  constructor(): this(DENIED)

  companion object {
    const val NEW = "new"
    const val DENIED = "denied"
    const val APPROVED = "approved"
  }
}

data class CurrentBabyRequests(val requests: MutableList<ProcessingBabyRequest>) {
  fun add(request: IncomingBabyRequest) {
    requests.add(ProcessingBabyRequest(request.name))
  }

  constructor() : this(mutableListOf<ProcessingBabyRequest>())
}

