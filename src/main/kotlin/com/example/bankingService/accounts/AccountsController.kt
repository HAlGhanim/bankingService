package com.example.bankingService.accounts


import com.example.bankingService.users.UsersService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class AccountsController(private val accountsService: AccountsService, private val usersService: UsersService) {

    @GetMapping("/accounts/v1/accounts")
    fun accounts() = accountsService.listAccounts()

    @GetMapping("/accounts/v1/id/{accountId}")
    fun accountById(@PathVariable accountId: Long) = accountsService.getAccountById(accountId)

    @GetMapping("/accounts/v1/number/{accountNumber}")
    fun accountByNumber(@PathVariable accountNumber: String) = accountsService.getAccountByAccountNumber(accountNumber)

    @PostMapping("/accounts/v1/accounts/{accountNumber}/close")
    fun closeAccount(@PathVariable accountNumber: String): AccountEntity = accountsService.closeAccount(accountNumber)

    @PostMapping("/accounts/v1/accounts/{accountNumber}/activate")
    fun activateAccount(@PathVariable accountNumber: String): AccountEntity = accountsService.activateAccount(accountNumber)

    @PostMapping("/accounts/v1/accounts")
    fun createAccount(@RequestBody request: AccountRequest): AccountEntity {
        val user = usersService.getUserById(request.userId)
        val account = AccountEntity(
            user = user,
            balance = request.balance,
            isActive = request.isActive,
            accountNumber = request.accountNumber,
        )
        return accountsService.createAccount(account)
    }

    data class AccountRequest(
        val userId: Long,
        val balance: Double,
        val isActive: Boolean,
        val accountNumber: String
    )
}