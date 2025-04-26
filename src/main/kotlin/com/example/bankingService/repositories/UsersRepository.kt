package com.example.bankingService.repositories

import com.example.bankingService.entities.UserEntity
import jakarta.inject.Named
import org.springframework.data.jpa.repository.JpaRepository

@Named
interface UsersRepository : JpaRepository<UserEntity, Long> {
    fun findByUsername(username: String): UserEntity?
}