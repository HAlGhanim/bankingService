package com.example.bankingService.transactions


import com.example.bankingService.accounts.AccountsService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class TransactionsController(private val transactionsService: TransactionsService, private val accountsService: AccountsService) {

    @GetMapping("/transactions/v1/list")
    fun transactions() = transactionsService.listTransactions()

    @PostMapping("/transactions/v1/create")
    fun createTransaction(@RequestBody request: TransactionsRequest): TransactionsEntity {
        val sourceAccount = accountsService.getAccountById(request.sourceAccount)
        val destinationAccount = accountsService.getAccountById(request.destinationAccount)
        val transactions = TransactionsEntity(
            sourceAccount = sourceAccount,
            destinationAccount = destinationAccount,
            amount = request.amount,
        )
        return transactionsService.createTransaction(transactions)
    }

    data class TransactionsRequest(
        val sourceAccount: Long,
        val destinationAccount: Long,
        val amount: Double,
    )
}