package com.example.bankingService.transactions

import com.example.bankingService.accounts.AccountsService
import org.springframework.web.bind.annotation.*
import java.math.BigDecimal

@RestController
class TransactionsController(
    private val transactionsService: TransactionsService,
    private val accountsService: AccountsService
) {

    @PostMapping("/accounts/v1/accounts/transfer")
    fun transfer(@RequestBody request: TransferRequest): TransferResponse {
        val sourceAccount = accountsService.getAccountByAccountNumber(request.sourceAccount)
        val destinationAccount = accountsService.getAccountByAccountNumber(request.destinationAccount)

        if (sourceAccount.balance < request.amount.toDouble()) {
            throw IllegalArgumentException("Insufficient balance in source account")
        }

        val transaction = TransactionsEntity(
            sourceAccount = sourceAccount,
            destinationAccount = destinationAccount,
            amount = request.amount.toDouble()
        )

        sourceAccount.balance -= request.amount.toDouble()
        destinationAccount.balance += request.amount.toDouble()

        accountsService.updateAccount(sourceAccount)
        accountsService.updateAccount(destinationAccount)

        transactionsService.createTransaction(transaction)

        return TransferResponse(newBalance = BigDecimal.valueOf(sourceAccount.balance))
    }

    data class TransferRequest(
        val sourceAccount: String,
        val destinationAccount: String,
        val amount: BigDecimal
    )

    data class TransferResponse(
        val newBalance: BigDecimal
    )
}
