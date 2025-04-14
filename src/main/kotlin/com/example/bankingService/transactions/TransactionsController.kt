package com.example.bankingService.transactions


import com.example.bankingService.accounts.AccountsService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class TransactionsController(private val transactionsService: TransactionsService, private val accountsService: AccountsService) {

    @GetMapping("/accounts/{accountId}/transactions/v1/list")
    fun transactions(@PathVariable accountId: Long) = transactionsService.listTransactions(accountId)

    @PostMapping("accounts/transactions/v1/create")
    fun createTransaction(@RequestBody request: TransactionsRequest): TransactionsEntity {
        val sourceAccount = accountsService.getAccountById(request.sourceAccount)
        val destinationAccount = accountsService.getAccountById(request.destinationAccount)

        if (sourceAccount.balance < request.amount) {
            throw IllegalArgumentException("Insufficient balance in source account")
        }

        val transaction = TransactionsEntity(
            sourceAccount = sourceAccount,
            destinationAccount = destinationAccount,
            amount = request.amount,
        )

        sourceAccount.balance -= request.amount
        destinationAccount.balance += request.amount

        accountsService.createAccount(sourceAccount)
        accountsService.createAccount(destinationAccount)
        // I realize an updateAccount would be a better name but there is no point in creating another service that does the same as createAccount

        return transactionsService.createTransaction(transaction)
    }

    data class TransactionsRequest(
        val sourceAccount: Long,
        val destinationAccount: Long,
        val amount: Double,
    )
}