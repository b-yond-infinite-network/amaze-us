package com.byond.challenge.unit.application.controller

import com.byond.challenge.application.controller.UserResource
import com.byond.challenge.delivery.dto.request.UserRequestDto
import com.byond.challenge.domain.entity.User
import com.byond.challenge.domain.usecase.CreateUser
import com.byond.challenge.domain.usecase.GetUser
import com.byond.challenge.domain.usecase.ListUsers
import com.byond.challenge.domain.usecase.ModifyUser
import com.byond.challenge.util.mock
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.doNothing
import org.mockito.Mockito.verify

class UserResourceTest {

    private lateinit var createUser: CreateUser
    private lateinit var listUsers: ListUsers
    private lateinit var getUser: GetUser
    private lateinit var modifyUser: ModifyUser
    private lateinit var userResource: UserResource

    private lateinit var userRequestDto: UserRequestDto
    private lateinit var user: User

    private val limit = 20
    private val offset = 0
    private val userId = 1L

    @BeforeEach
    fun setUp() {
        createUser = mock()
        listUsers = mock()
        getUser = mock()
        modifyUser = mock()
        userResource = UserResource(createUser, listUsers, getUser, modifyUser)
    }

    @Test
    fun `test create user`() {
        // Given
        givenUserRequestDto()
        givenUser()

        // When
        `when`(createUser(userRequestDto)).thenReturn(user)
        val createdUser = userResource.createUser(userRequestDto)

        // Then
        assertNotNull(createdUser)
        verify(createUser).invoke(userRequestDto)
    }

    @Test
    fun `test list users`() {
        // Given
        givenUser()
        val listOfUsers = listOf(user)

        // When
        `when`(listUsers(limit, offset)).thenReturn(listOfUsers)
        val results = userResource.listUsers(limit, offset)

        // Then
        assertEquals(1, results.users.size)
        verify(listUsers).invoke(limit, offset)
    }

    @Test
    fun `test get user`() {
        // Given
        givenUser()

        // When
        `when`(getUser(userId)).thenReturn(user)
        val myUser = userResource.getUser(userId)

        // Then
        assertNotNull(myUser)
        verify(getUser).invoke(userId)
    }

    @Test
    fun `test modify user`() {
        // Given
        givenUserRequestDto()
        givenUser()

        // When
        doNothing().`when`(modifyUser).invoke(userId, userRequestDto)
        userResource.modifyUser(userId, userRequestDto)

        // Then
        verify(modifyUser).invoke(userId, userRequestDto)
    }

    private fun givenUserRequestDto() {
        userRequestDto = UserRequestDto(
            id = null,
            name = "Santiago",
            email = "santi@santi.com",
            description = "My name is santiago"
        )
    }

    private fun givenUser() {
        user = User(
            id = 1L,
            name = "Santiago",
            email = "santi@santi.com",
            description = "My name is santiago"
        )
    }
}
