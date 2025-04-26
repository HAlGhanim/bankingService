package com.example.bankingService.test

import com.example.bankingService.authentication.jwt.JwtService
import com.example.bankingService.repositories.UsersRepository
import com.example.bankingService.repositories.AccountsRepository
import com.example.bankingService.entities.AccountEntity
import com.example.bankingService.entities.UserEntity
import io.cucumber.java.en.Given
import io.cucumber.java.en.Then
import io.cucumber.java.en.When
import org.junit.jupiter.api.Assertions.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.*
import org.springframework.security.crypto.password.PasswordEncoder
import java.math.BigDecimal

class TransactionsSteps {

    @Autowired
    private lateinit var testRestTemplate: TestRestTemplate

    @Autowired
    private lateinit var jwtService: JwtService

    @Autowired
    private lateinit var usersRepository: UsersRepository

    @Autowired
    private lateinit var accountsRepository: AccountsRepository

    @Autowired
    private lateinit var passwordEncoder: PasswordEncoder

    private var token: String = ""
    private var response: ResponseEntity<String>? = null

    @Given("I am authenticated for transactions operations as {string}")
    fun iAmAuthenticatedForTransactions(username: String) {
        token = jwtService.generateToken(username)
    }

    @Given("Two accounts exist for user {string}")
    fun twoAccountsExistForUser(username: String) {
        var user = usersRepository.findByUsername(username)

        if (user == null) {
            user = usersRepository.save(
                UserEntity(
                    username = username,
                    password = passwordEncoder.encode("defaultpassword")
                )
            )
        }

        val account1 = AccountEntity(
            user = user,
            accountNumber = "SRC123",
            name = "Source Account",
            balance = BigDecimal(1000),
            isActive = true
        )

        val account2 = AccountEntity(
            user = user,
            accountNumber = "DST123",
            name = "Destination Account",
            balance = BigDecimal(500),
            isActive = true
        )

        accountsRepository.saveAll(listOf(account1, account2))
    }

    @When("I send a POST request for transactions to {string} with body")
    fun iSendPostRequestForTransactions(endpoint: String, body: String) {
        val headers = HttpHeaders().apply {
            contentType = MediaType.APPLICATION_JSON
            setBearerAuth(token)
        }
        response = testRestTemplate.exchange(endpoint, HttpMethod.POST, HttpEntity(body, headers), String::class.java)
    }

    @Then("the transactions response status should be {int}")
    fun theTransactionsResponseStatusShouldBe(expectedStatus: Int) {
        assertEquals(expectedStatus, response?.statusCode?.value())
    }
}
