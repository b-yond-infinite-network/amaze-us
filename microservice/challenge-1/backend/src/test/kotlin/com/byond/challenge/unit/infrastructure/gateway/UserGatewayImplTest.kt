package com.byond.challenge.unit.infrastructure.gateway

import com.byond.challenge.domain.entity.User
import com.byond.challenge.domain.external.UserGateway
import com.byond.challenge.infrastructure.gateway.UserGatewayImpl
import com.byond.challenge.infrastructure.repository.UserRepository
import com.byond.challenge.util.mock
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify

class UserGatewayImplTest {

    private lateinit var userRepository: UserRepository
    private lateinit var userGateway: UserGateway

    private lateinit var user: User
    private val userId = 1L

    @BeforeEach
    fun setUp() {
        userRepository = mock()
        userGateway = UserGatewayImpl(userRepository)
    }

    @Test
    fun `test save user`() {
        // Given
        givenUser()
        val userToSave = User(null, "Santiago", "santi@santi.com", "This is a description")

        // When
        `when`(userRepository.saveUser(userToSave)).thenReturn(user)
        val savedUser = userGateway.saveUser(userToSave)

        // Then
        assertNotNull(savedUser)
        verify(userRepository).saveUser(userToSave)
    }

    @Test
    fun `test list users`() {
        // Given
        givenUser()
        val usersList = listOf(user)

        // When
        `when`(userRepository.listUsers()).thenReturn(usersList)
        val results = userGateway.listUsers()

        // Then
        assertEquals(1, results.size)
        verify(userRepository).listUsers()
    }

    @Test
    fun `test get user`() {
        // Given
        givenUser()

        // When
        `when`(userRepository.getUser(userId)).thenReturn(user)
        val myUser = userGateway.getUser(userId)

        // Then
        assertNotNull(myUser)
        verify(userRepository).getUser(userId)
    }

    private fun givenUser() {
        user = User(
            id = 1L,
            name = "Santiago",
            email = "santi@santi.com",
            description = "This is a description"
        )
    }
}
