package com.example.bankingService.transactions

import jakarta.inject.Named

@Named
class TransactionsService(
    private val transactionsRepository: TransactionsRepository,
) {

    fun listTransactions(): List<Transactions> = transactionsRepository.findAll().map {
        Transactions(
            sourceAccount = it.sourceAccount.id,
            destinationAccount = it.destinationAccount.id,
            amount = it.amount
        )
    }

    fun createTransaction(account: TransactionsEntity): TransactionsEntity {
        return transactionsRepository.save(account)
    }
}

data class Transactions(
    val sourceAccount: Long?,
    val destinationAccount: Long?,
    val amount: Double,
)