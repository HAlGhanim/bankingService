package com.example.bankingService.services

import com.example.bankingService.entities.AccountEntity
import com.example.bankingService.entities.TransactionsEntity
import com.example.bankingService.repositories.TransactionsRepository
import jakarta.inject.Named
import java.math.BigDecimal

@Named
class TransactionsService(
    private val transactionsRepository: TransactionsRepository,
    private val accountsService: AccountsService,
) {

    fun listTransactions(accountNumber: String): List<TransactionDto> {
        val account = accountsService.getAccountByAccountNumber(accountNumber)

        return transactionsRepository.findAll().filter {
            it.sourceAccount.id == account.id || it.destinationAccount.id == account.id
        }.map {
            TransactionDto(
                sourceAccount = it.sourceAccount.accountNumber,
                destinationAccount = it.destinationAccount.accountNumber,
                amount = it.amount
            )
        }
    }

    fun createTransaction(
        sourceAccount: AccountEntity,
        destinationAccount: AccountEntity,
        amount: BigDecimal
    ): TransactionsEntity {
        val transaction = TransactionsEntity(
            sourceAccount = sourceAccount,
            destinationAccount = destinationAccount,
            amount = amount
        )
        return transactionsRepository.save(transaction)
    }
}

data class TransactionDto(
    val sourceAccount: String,
    val destinationAccount: String,
    val amount: BigDecimal
)
