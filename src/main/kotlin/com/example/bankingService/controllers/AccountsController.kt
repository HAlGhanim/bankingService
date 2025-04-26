package com.example.bankingService.controllers

import com.example.bankingService.entities.AccountEntity
import com.example.bankingService.services.Account
import com.example.bankingService.services.AccountsService
import com.example.bankingService.services.UsersService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.math.BigDecimal

@RestController
class AccountsController(private val accountsService: AccountsService, private val usersService: UsersService) {

    @GetMapping("/accounts/v1/")
    fun accounts() = mapOf("accounts" to accountsService.listAccounts())

    @GetMapping("/accounts/v1/id/{accountId}")
    fun accountById(@PathVariable accountId: Long) = accountsService.mapAccount(accountsService.getAccountById(accountId))

    @GetMapping("/accounts/v1/number/{accountNumber}")
    fun accountByNumber(@PathVariable accountNumber: String) = accountsService.mapAccount(accountsService.getAccountByAccountNumber(accountNumber))

    @PostMapping("/accounts/v1/{accountNumber}/close")
    fun closeAccount(@PathVariable accountNumber: String): ResponseEntity<Void> {
        accountsService.closeAccount(accountNumber)
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build()
    }

    @PostMapping("/accounts/v1/{accountNumber}/activate")
    fun activateAccount(@PathVariable accountNumber: String): ResponseEntity<Void> {
        accountsService.activateAccount(accountNumber)
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build()
    }

    @PostMapping("/accounts/v1/create")
    fun createAccount(@RequestBody request: AccountRequest): Account {
        val user = usersService.getUserById(request.userId)
        val accountEntity = AccountEntity(
            user = user,
            accountNumber = request.accountNumber,
            name = request.name,
            balance = request.balance,
            isActive = request.isActive
        )
        val savedAccountEntity = accountsService.createAccount(accountEntity)
        return accountsService.mapAccount(savedAccountEntity)
    }


    data class AccountRequest(
        val userId: Long,
        val balance: BigDecimal,
        val isActive: Boolean,
        val accountNumber: String,
        val name: String
    )

}