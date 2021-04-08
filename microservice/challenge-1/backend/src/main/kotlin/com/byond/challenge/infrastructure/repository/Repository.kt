package com.byond.challenge.infrastructure.repository

import com.byond.challenge.infrastructure.repository.model.UserModel
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface Repository : JpaRepository<UserModel, Long>