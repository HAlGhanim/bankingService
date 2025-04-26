package com.example.bankingService.transactions

import com.example.bankingService.accounts.AccountsService
import jakarta.inject.Named

@Named
class TransactionsService(
    private val transactionsRepository: TransactionsRepository,
) {

    fun createTransaction(transaction: TransactionsEntity): TransactionsEntity {
        return transactionsRepository.save(transaction)
    }

}
