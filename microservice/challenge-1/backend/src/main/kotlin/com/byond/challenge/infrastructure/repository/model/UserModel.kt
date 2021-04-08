package com.byond.challenge.infrastructure.repository.model

import com.byond.challenge.domain.entity.User
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "users", schema = "public")
data class UserModel(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    var id: Long? = 0,
    @Column
    var name: String,
    @Column
    var email: String,
    @Column
    var description: String
) {

    fun toUser(): User {
        return User(
            id = id,
            name = name,
            email = email,
            description = description
        )
    }
}
