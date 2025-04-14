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
//    comment to initial push!

    @PostMapping("/accounts/v1/accounts/{accountNumber}/close")
    fun closeAccount(@PathVariable accountNumber: String): AccountEntity = accountsService.closeAccount(accountNumber)

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