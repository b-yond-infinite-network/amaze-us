package amaze.us.model

import amaze.us.db.BabyRequest

data class PopulationAmount(val amount: String) {
  constructor() : this("N/A")
}

data class IncomingBabyRequest(val name: String, val author: String) {
  constructor() : this("N/A", "Unknown")
}

data class Decision(val status: String, val reviewer: String) {
  constructor() : this(DENIED, "System")

  companion object {
    const val NEW = "new"
    const val DENIED = "denied"
    const val APPROVED = "approved"
  }
}

data class ListOfBabyRequest constructor(val requests: List<BabyRequest>) {

  constructor() : this(mutableListOf<BabyRequest>())
}


