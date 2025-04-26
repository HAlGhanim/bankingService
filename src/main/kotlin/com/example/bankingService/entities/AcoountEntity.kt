package com.example.bankingService.entities

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import java.math.BigDecimal

@Entity
@Table(name = "accounts")
data class AccountEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @ManyToOne
    val user: UserEntity,

    val accountNumber: String,
    var name: String,
    var balance: BigDecimal,
    var isActive: Boolean = true
) {
    constructor() : this(null, UserEntity(), "", "", BigDecimal.ZERO, true)
}
