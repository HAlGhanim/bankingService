package com.example.bankingService.users

import jakarta.inject.Named
import jakarta.persistence.*
import org.springframework.data.jpa.repository.JpaRepository

@Named
interface UsersRepository : JpaRepository<UserEntity, Long>

@Entity
@Table(name = "users")
data class UserEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    var username: String,
    var password: String
){
    constructor() : this(null, "", "")
}