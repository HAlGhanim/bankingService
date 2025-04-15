package com.example.bankingService.accounts

import jakarta.inject.Named
import kotlin.random.Random

@Named
class AccountsService(
    private val accountsRepository: AccountsRepository,
) {

    fun getAccountByAccountNumber(accountNumber: String): AccountEntity {
        return accountsRepository.findByAccountNumber(accountNumber)
            .orElseThrow { IllegalArgumentException("Account not found") }
    }


    fun getAccountById(accountId: Long): AccountEntity {
        return accountsRepository.findById(accountId).orElseThrow()
    }

    fun listAccounts(): List<Account> = accountsRepository.findAll().map {
        Account(
            userId = it.user.id,
            accountNumber = it.accountNumber,
            balance = it.balance,
            isActive = it.isActive
        )
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
    fun generateAccountNumber(): String {
        return List(10) { Random.nextInt(0, 10) }.joinToString("")
    }

    fun updateAccount(account: AccountEntity): AccountEntity {
        return accountsRepository.save(account)
    }
}

data class Account(
    val userId: Long?,
    val accountNumber: String,
    var balance: Double,
    var isActive: Boolean
)
