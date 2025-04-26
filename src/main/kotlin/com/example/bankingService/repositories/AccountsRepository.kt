package com.example.bankingService.repositories

import com.example.bankingService.entities.AccountEntity
import jakarta.inject.Named
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

@Named
interface AccountsRepository : JpaRepository<AccountEntity, Long> {
    fun findByAccountNumber(accountNumber: String): Optional<AccountEntity>
}