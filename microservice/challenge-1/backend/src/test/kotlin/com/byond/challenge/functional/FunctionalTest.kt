package com.byond.challenge.functional

import com.byond.challenge.infrastructure.repository.Repository
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.test.context.ActiveProfiles

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("test")
abstract class FunctionalTest {

    @Autowired
    protected lateinit var testRestTemplate: TestRestTemplate
    @Autowired
    protected lateinit var repository: Repository
    protected lateinit var controller: String

    @BeforeEach
    fun setUp() {
        controller = "${testRestTemplate.rootUri}/users"
    }

    @AfterEach
    fun tearDown() {
        repository.deleteAll()
    }
}
