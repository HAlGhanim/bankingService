package com.example.bankingService.test

import com.example.bankingService.authentication.jwt.JwtService
import io.cucumber.java.en.Given
import io.cucumber.java.en.Then
import io.cucumber.java.en.When
import org.junit.jupiter.api.Assertions.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.*

class KycSteps {

    @Autowired
    private lateinit var testRestTemplate: TestRestTemplate

    @Autowired
    private lateinit var jwtService: JwtService

    private var token: String = ""
    private var response: ResponseEntity<String>? = null

    @Given("I am authenticated for KYC operations as {string}")
    fun iAmAuthenticatedForKyc(username: String) {
        token = jwtService.generateToken(username)
    }

    @When("I send a POST request for kyc to {string} with body")
    fun iSendPostRequestForKyc(endpoint: String, body: String) {
        val headers = HttpHeaders().apply {
            contentType = MediaType.APPLICATION_JSON
            setBearerAuth(token)
        }
        response = testRestTemplate.exchange(endpoint, HttpMethod.POST, HttpEntity(body, headers), String::class.java)
    }

    @When("I send a GET request for kyc to {string}")
    fun iSendGetRequestForKyc(endpoint: String) {
        val headers = HttpHeaders().apply { setBearerAuth(token) }
        response = testRestTemplate.exchange(endpoint, HttpMethod.GET, HttpEntity<String>(headers), String::class.java)
    }

    @Then("the kyc response status should be {int}")
    fun theKycResponseStatusShouldBe(expectedStatus: Int) {
        assertEquals(expectedStatus, response?.statusCode?.value())
    }
}
