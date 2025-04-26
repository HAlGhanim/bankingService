package com.example.bankingService.controllers

import com.example.bankingService.services.AccountsService
import com.example.bankingService.services.TransactionsService
import org.springframework.web.bind.annotation.*
import java.math.BigDecimal

@RestController
@RequestMapping("/accounts/v1")
class TransactionsController(
    private val transactionsService: TransactionsService,
    private val accountsService: AccountsService
) {

    @GetMapping("/transactions/{accountNumber}/list")
    fun listTransactions(@PathVariable accountNumber: String) =
        mapOf("transactions" to transactionsService.listTransactions(accountNumber))

    @PostMapping("/transactions/transfer")
    fun createTransaction(@RequestBody request: TransactionsRequest): Map<String, BigDecimal> {
        val sourceAccount = accountsService.getAccountByAccountNumber(request.sourceAccount)
        val destinationAccount = accountsService.getAccountByAccountNumber(request.destinationAccount)

        if (sourceAccount.balance < request.amount) {
            throw IllegalArgumentException("Insufficient balance in source account")
        }

        sourceAccount.balance = sourceAccount.balance.minus(request.amount)
        destinationAccount.balance = destinationAccount.balance.plus(request.amount)

        accountsService.createAccount(sourceAccount)
        accountsService.createAccount(destinationAccount)

        transactionsService.createTransaction(sourceAccount, destinationAccount, request.amount)

        return mapOf("newBalance" to sourceAccount.balance)
    }

    data class TransactionsRequest(
        val sourceAccount: String,
        val destinationAccount: String,
        val amount: BigDecimal
    )
}
