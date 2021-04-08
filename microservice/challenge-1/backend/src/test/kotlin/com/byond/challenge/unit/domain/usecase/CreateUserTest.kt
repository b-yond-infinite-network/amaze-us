package com.byond.challenge.unit.domain.usecase

import com.byond.challenge.delivery.dto.request.UserRequestDto
import com.byond.challenge.domain.entity.User
import com.byond.challenge.domain.external.UserGateway
import com.byond.challenge.domain.usecase.CreateUser
import com.byond.challenge.util.mock
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify

class CreateUserTest {

    private lateinit var userGateway: UserGateway
    private lateinit var createUser: CreateUser

    private lateinit var userRequestDto: UserRequestDto
    private lateinit var user: User

    @BeforeEach
    fun setUp() {
        userGateway = mock()
        createUser = CreateUser(userGateway)
    }

    @Test
    fun `test create user`() {
        // Given
        givenUser()
        givenUserRequest()
        val userMapped = userRequestDto.toUser()

        // When
        `when`(userGateway.saveUser(userMapped)).thenReturn(user)
        val savedUser = createUser(userRequestDto)

        // Then
        assertNotNull(savedUser)
        verify(userGateway).saveUser(userMapped)
    }

    private fun givenUserRequest() {
        userRequestDto = UserRequestDto(null, "Santi", "santi@santi.com", "Description")
    }

    private fun givenUser() {
        user = User(1L, "Santi", "santi@santi.com", "Description")
    }
}