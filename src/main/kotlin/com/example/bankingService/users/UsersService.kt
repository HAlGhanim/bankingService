package com.example.bankingService.users

import jakarta.inject.Named
import kotlin.jvm.optionals.getOrNull

@Named
class UsersService(
    private val usersRepository: UsersRepository,
) {

    fun getUserById(userId: Long): UserEntity {
        return usersRepository.findById(userId).getOrNull()
    }

    fun listUsers(): List<User> = usersRepository.findAll().map {
        User(
            username = it.username,
            password = it.password
        )
    }

    fun createUser(user: UserEntity): UserEntity {
        return usersRepository.save(user)
    }
}

data class User(
    val username: String,
    val password: String
)