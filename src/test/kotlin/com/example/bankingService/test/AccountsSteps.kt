package com.example.bankingService.test

import com.example.bankingService.authentication.jwt.JwtService
import io.cucumber.java.en.Given
import io.cucumber.java.en.Then
import io.cucumber.java.en.When
import org.junit.jupiter.api.Assertions.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.*

class AccountsSteps {

    @Autowired
    private lateinit var testRestTemplate: TestRestTemplate

    @Autowired
    private lateinit var jwtService: JwtService

    private var response: ResponseEntity<String>? = null
    private var token: String = ""

    @Given("I am authenticated for accounts operations as {string}")
    fun iAmAuthenticatedForAccounts(username: String) {
        token = jwtService.generateToken(username)
    }

    @When("I send a POST request for accounts to {string} with body")
    fun iSendPostRequestForAccounts(endpoint: String, body: String) {
        val headers = HttpHeaders().apply {
            contentType = MediaType.APPLICATION_JSON
            setBearerAuth(token)
        }
        response = testRestTemplate.exchange(endpoint, HttpMethod.POST, HttpEntity(body, headers), String::class.java)
    }

    @When("I send a POST request for accounts to {string} with empty body")
    fun iSendPostRequestForAccountsEmpty(endpoint: String) {
        val headers = HttpHeaders().apply { setBearerAuth(token) }
        response = testRestTemplate.exchange(endpoint, HttpMethod.POST, HttpEntity<String>(headers), String::class.java)
    }

    @When("I send a GET request for accounts to {string}")
    fun iSendGetRequestForAccounts(endpoint: String) {
        val headers = HttpHeaders().apply { setBearerAuth(token) }
        response = testRestTemplate.exchange(endpoint, HttpMethod.GET, HttpEntity<String>(headers), String::class.java)
    }

    @Then("the accounts response status should be {int}")
    fun theAccountsResponseStatusShouldBe(expectedStatus: Int) {
        assertEquals(expectedStatus, response?.statusCode?.value())
    }
}
