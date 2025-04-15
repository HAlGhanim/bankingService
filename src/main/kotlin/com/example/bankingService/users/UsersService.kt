package com.example.bankingService.users

import jakarta.inject.Named
import kotlin.jvm.optionals.getOrNull

@Named
class UsersService(
    private val usersRepository: UsersRepository,
) {

    fun getUserById(userId: Long): UserEntity {
        return usersRepository.findById(userId).orElseThrow()
    }

    fun getUserDtoById(userId: Long): User {
        val user = usersRepository.findById(userId).orElseThrow()
        return User(
            id = user.id!!,
            username = user.username
        )
    }


    fun listUsers(): List<User> = usersRepository.findAll().map {
        User(
            id = it.id!!,
            username = it.username,
        )
    }

    fun createUser(user: UserEntity): UserEntity {
        return usersRepository.save(user)
    }
}

data class User(
    val id: Long,
    val username: String,
)