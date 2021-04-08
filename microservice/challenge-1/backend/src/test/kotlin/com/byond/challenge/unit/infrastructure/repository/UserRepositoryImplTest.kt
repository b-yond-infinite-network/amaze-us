package com.byond.challenge.unit.infrastructure.repository

import com.byond.challenge.domain.entity.User
import com.byond.challenge.infrastructure.repository.Repository
import com.byond.challenge.infrastructure.repository.UserRepository
import com.byond.challenge.infrastructure.repository.impl.UserRepositoryImpl
import com.byond.challenge.infrastructure.repository.model.UserModel
import com.byond.challenge.util.mock
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import java.util.Optional

class UserRepositoryImplTest {

    private lateinit var repository: Repository
    private lateinit var userRepository: UserRepository

    private lateinit var user: User
    private lateinit var userModel: UserModel
    private val userId = 1L

    @BeforeEach
    fun setUp() {
        repository = mock()
        userRepository = UserRepositoryImpl(repository)
    }

    @Test
    fun `test save user`() {
        // Given
        givenUser()
        givenUserModel()
        val model = user.toUserModel()

        // When
        `when`(repository.save(model)).thenReturn(userModel)
        val savedUser = userRepository.saveUser(user)

        // Then
        assertNotNull(savedUser)
        verify(repository).save(model)
    }

    @Test
    fun `test list users`() {
        // Given
        givenUserModel()
        val list = listOf(userModel)

        // When
        `when`(repository.findAll()).thenReturn(list)
        val results = userRepository.listUsers()

        // Then
        assertEquals(1, results.size)
        verify(repository).findAll()
    }

    @Test
    fun `test get user`() {
        // Given
        givenUserModel()

        // When
        `when`(repository.findById(userId)).thenReturn(Optional.of(userModel))
        val myUser = userRepository.getUser(userId)

        // Then
        assertNotNull(myUser)
        verify(repository).findById(userId)
    }

    private fun givenUser() {
        user = User(
            id = null,
            name = "Santiago",
            email = "santi@santi.com",
            description = "This is a description"
        )
    }

    private fun givenUserModel() {
        userModel = UserModel(
            id = 1L,
            name = "Santiago",
            email = "santi@santi.com",
            description = "This is a description"
        )
    }
}
