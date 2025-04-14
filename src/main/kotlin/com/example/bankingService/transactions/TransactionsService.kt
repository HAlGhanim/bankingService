package com.example.bankingService.transactions

import com.example.bankingService.accounts.AccountsService
import jakarta.inject.Named

@Named
class TransactionsService(
    private val transactionsRepository: TransactionsRepository,
    private val accountsService: AccountsService,
) {

    fun listTransactions(accountId: Long): List<Transactions> {
        val account = accountsService.getAccountById(accountId)

        return transactionsRepository.findAll().filter {
            it.sourceAccount.id == account.id || it.destinationAccount.id == account.id
        }.map {
            Transactions(
                sourceAccount = it.sourceAccount.id,
                destinationAccount = it.destinationAccount.id,
                amount = it.amount
            )
        }
    }

    fun createTransaction(transaction: TransactionsEntity): TransactionsEntity {
        return transactionsRepository.save(transaction)
    }
}

data class Transactions(
    val sourceAccount: Long?,
    val destinationAccount: Long?,
    val amount: Double,
)
