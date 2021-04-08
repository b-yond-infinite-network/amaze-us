package com.byond.challenge.application.config

import com.byond.challenge.domain.external.UserGateway
import com.byond.challenge.domain.usecase.CreateUser
import com.byond.challenge.domain.usecase.GetUser
import com.byond.challenge.domain.usecase.ListUsers
import com.byond.challenge.domain.usecase.ModifyUser
import com.byond.challenge.infrastructure.gateway.UserGatewayImpl
import com.byond.challenge.infrastructure.repository.Repository
import com.byond.challenge.infrastructure.repository.UserRepository
import com.byond.challenge.infrastructure.repository.impl.UserRepositoryImpl
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class UserConfiguration {

    @Bean
    fun createUser(userGateway: UserGateway): CreateUser {
        return CreateUser(userGateway)
    }

    @Bean
    fun listUsers(userGateway: UserGateway): ListUsers {
        return ListUsers(userGateway)
    }

    @Bean
    fun getUser(userGateway: UserGateway): GetUser {
        return GetUser(userGateway)
    }

    @Bean
    fun modifyUser(userGateway: UserGateway): ModifyUser {
        return ModifyUser(userGateway)
    }

    @Bean
    fun userGateway(userRepository: UserRepository): UserGateway {
        return UserGatewayImpl(userRepository)
    }

    @Bean
    fun userRepository(repository: Repository): UserRepository {
        return UserRepositoryImpl(repository)
    }
}
