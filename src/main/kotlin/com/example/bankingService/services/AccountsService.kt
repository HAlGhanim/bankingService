package com.example.bankingService.services

import com.example.bankingService.entities.AccountEntity
import com.example.bankingService.repositories.AccountsRepository
import jakarta.inject.Named
import java.math.BigDecimal

@Named
class AccountsService(
    private val accountsRepository: AccountsRepository,
) {

    fun getAccountByAccountNumber(accountNumber: String): AccountEntity {
        return accountsRepository.findByAccountNumber(accountNumber).orElseThrow()
    }

    fun getAccountById(accountId: Long): AccountEntity {
        return accountsRepository.findById(accountId).orElseThrow()
    }

    fun listAccounts(): List<Account> = accountsRepository.findAll().map {
        mapAccount(it)
    }

    fun closeAccount(accountNumber: String): AccountEntity {
        val account = getAccountByAccountNumber(accountNumber)
        account.isActive = false
        return accountsRepository.save(account)
    }

    fun activateAccount(accountNumber: String): AccountEntity {
        val account = getAccountByAccountNumber(accountNumber)
        account.isActive = true
        return accountsRepository.save(account)
    }

    fun createAccount(account: AccountEntity): AccountEntity {
        return accountsRepository.save(account)
    }

    fun mapAccount(account: AccountEntity): Account {
        return Account(
            userId = account.user.id,
            balance = account.balance,
            isActive = account.isActive,
            accountNumber = account.accountNumber,
            name = account.name
        )
    }
}

data class Account(
    val userId: Long?,
    var balance: BigDecimal,
    var isActive: Boolean,
    val accountNumber: String,
    val name: String
)
