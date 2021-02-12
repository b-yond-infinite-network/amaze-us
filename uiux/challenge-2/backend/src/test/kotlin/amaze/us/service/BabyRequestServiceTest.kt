package amaze.us.service

import amaze.us.db.BabyRequest
import amaze.us.model.Decision
import amaze.us.model.Decision.Companion.APPROVED
import amaze.us.model.Decision.Companion.DENIED
import amaze.us.model.Decision.Companion.NEW
import amaze.us.model.PopulationAmount
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.test.context.junit.jupiter.SpringExtension

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension::class)
class BabyRequestServiceTest {

  @MockkBean(relaxed = true)
  private lateinit var mongoTemplate: MongoTemplate

  @Autowired
  private lateinit var populationService: PopulationService

  @Test
  fun getRequestsWithApprovedTest() {
    val defaultResponse = mutableListOf(BabyRequest("", "", NEW))
    every { mongoTemplate.find(any(), BabyRequest::class.java) } returns defaultResponse
    assertEquals(PopulationAmount("2001"), populationService.count())
  }

  @Test
  fun getCurrentRequestsTest() {
    val defaultResponse = mutableListOf(BabyRequest("", "", APPROVED))
    every { mongoTemplate.find(any(), BabyRequest::class.java) } returns defaultResponse
    assertEquals(1, populationService.pendingBabyRequests().requests.size)
  }

  @Test
  fun getProcessedRequestsTest() {
    val defaultResponse = mutableListOf(BabyRequest("", "", APPROVED, 1L, "", "Decider"))
    every { mongoTemplate.find(any(), BabyRequest::class.java) } returns defaultResponse
    assertEquals(1, populationService.processedBabyRequests().requests.size)
  }

  @Test
  fun getRequestsFailTest() {
    every { mongoTemplate.find(any(), BabyRequest::class.java) } throws RuntimeException()
    assertEquals(0, populationService.processedBabyRequests().requests.size)
  }

  @Test
  fun updateRequestsTest() {
    every { mongoTemplate.findAndModify(any(), any(), BabyRequest::class.java) } returns BabyRequest()
    assertEquals(true, populationService.processBabyRequestUpdate("1", Decision(DENIED, "a")))
    assertEquals(true, populationService.processBabyRequestUpdate("1", Decision("DENIED", "a")))
    assertEquals(true, populationService.processBabyRequestUpdate("1", Decision(APPROVED, "a")))
    assertEquals(true, populationService.processBabyRequestUpdate("1", Decision("APPROVED", "a")))
  }

  @Test
  fun updateRequestsNoIdMatchTest() {
    every { mongoTemplate.findAndModify(any(), any(), BabyRequest::class.java) } returns null
    assertEquals(false, populationService.processBabyRequestUpdate("1", Decision(DENIED, "a")))
    assertEquals(false, populationService.processBabyRequestUpdate("1", Decision("DENIED", "a")))
    assertEquals(false, populationService.processBabyRequestUpdate("1", Decision(APPROVED, "a")))
    assertEquals(false, populationService.processBabyRequestUpdate("1", Decision("APPROVED", "a")))
    assertEquals(false, populationService.processBabyRequestUpdate("1", Decision("random", "a")))
  }
}