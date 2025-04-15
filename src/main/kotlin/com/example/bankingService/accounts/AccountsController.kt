package com.example.bankingService.accounts

import com.example.bankingService.users.UsersService
import org.springframework.web.bind.annotation.*
import kotlin.random.Random

@RestController
class AccountsController(
    private val accountsService: AccountsService,
    private val usersService: UsersService
) {

    @GetMapping("/accounts/v1/accounts")
    fun accounts(): List<Account> = accountsService.listAccounts()

    @GetMapping("/accounts/v1/id/{accountId}")
    fun accountById(@PathVariable accountId: Long): AccountEntity =
        accountsService.getAccountById(accountId)

    @GetMapping("/accounts/v1/number/{accountNumber}")
    fun accountByNumber(@PathVariable accountNumber: String): AccountEntity =
        accountsService.getAccountByAccountNumber(accountNumber)

    @PostMapping("/accounts/v1/accounts/{accountNumber}/close")
    fun closeAccount(@PathVariable accountNumber: String): AccountEntity =
        accountsService.closeAccount(accountNumber)

    @PostMapping("/accounts/v1/accounts/{accountNumber}/activate")
    fun activateAccount(@PathVariable accountNumber: String): AccountEntity =
        accountsService.activateAccount(accountNumber)

    @PostMapping("/accounts/v1/accounts")
    fun createAccount(@RequestBody request: AccountRequest): Account {
        val user = usersService.getUserById(request.userId)
        val generatedAccountNumber = accountsService.generateAccountNumber()

        val account = AccountEntity(
            user = user,
            balance = request.balance,
            isActive = true,
            accountNumber = generatedAccountNumber
        )

        val savedAccount = accountsService.createAccount(account)
        return Account(
            userId = savedAccount.user.id,
            accountNumber = savedAccount.accountNumber,
            balance = savedAccount.balance,
            isActive = savedAccount.isActive
        )
    }

    data class AccountRequest(
        val userId: Long,
        val balance: Double
    )
}
