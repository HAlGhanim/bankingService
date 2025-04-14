package com.example.bankingService.accounts

import jakarta.inject.Named

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
        Account(
            userId = it.user.id,
            balance = it.balance,
            isActive = it.isActive,
            accountNumber = it.accountNumber
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

}

data class Account(
    val userId: Long?,
    var balance: Double,
    var isActive: Boolean,
    val accountNumber: String
)