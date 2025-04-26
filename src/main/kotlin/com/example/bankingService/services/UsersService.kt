package com.example.bankingService.services

import com.example.bankingService.entities.UserEntity
import com.example.bankingService.repositories.UsersRepository
import jakarta.inject.Named

@Named
class UsersService(
    private val usersRepository: UsersRepository,
) {

    fun getUserById(userId: Long): UserEntity {
        return usersRepository.findById(userId).orElseThrow()
    }

    fun listUsers(): List<UserResponseDto> = usersRepository.findAll().map {
        UserResponseDto(
            id = it.id,
            username = it.username,
        )
    }

    fun createUser(user: UserEntity): UserEntity {
        return usersRepository.save(user)
    }
}

data class UserResponseDto(
    val id: Long?,
    val username: String
)
