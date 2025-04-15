package com.example.bankingService.accounts

import com.example.bankingService.users.UserEntity
import jakarta.inject.Named
import jakarta.persistence.*
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

@Named
interface AccountsRepository : JpaRepository<AccountEntity, Long> {
    fun findByAccountNumber(accountNumber: String): Optional<AccountEntity>
}


@Entity
@Table(name = "accounts")
data class AccountEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @ManyToOne
    val user: UserEntity,

    var balance: Double,
    var isActive: Boolean,
    val accountNumber: String
) {
    constructor() : this(null, UserEntity(), 0.0, true, "")
}