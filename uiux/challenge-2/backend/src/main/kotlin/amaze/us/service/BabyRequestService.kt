package amaze.us.service

import amaze.us.config.LOGGER
import amaze.us.db.BabyRequest
import amaze.us.db.BabyUpdate
import amaze.us.model.Decision.Companion.APPROVED
import amaze.us.model.Decision.Companion.DENIED
import amaze.us.model.Decision.Companion.NEW
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.query.Update
import org.springframework.stereotype.Service

@Service
class BabyRequestService {

  @Autowired
  private lateinit var mongoTemplate: MongoTemplate

  companion object {
    internal val new = Query(Criteria.where("status").`is`(NEW))
    internal val approved = Query(Criteria.where("status").`is`(APPROVED))
  }

  fun getRequests(query: Query): MutableList<BabyRequest> = try {
    mongoTemplate.find(query, BabyRequest::class.java)
  } catch (e: Exception) {
    LOGGER.error("Error while getting the requests: $e", e)
    mutableListOf()
  }

  fun createRequest(request: BabyRequest) = LOGGER.info("Baby request has been added ${mongoTemplate.insert(request)}")

  fun updateRequest(id: String, babyUpdate: BabyUpdate): Boolean {
    LOGGER.info("Update for $id - $babyUpdate")
    val update = Update().also {
      it.set("status", babyUpdate.status)
      it.set("reviewer", babyUpdate.reviewer)
    }
    return mongoTemplate.findAndModify(Query(Criteria.where("id").`is`(id)), update, BabyRequest::class.java) != null
  }
}