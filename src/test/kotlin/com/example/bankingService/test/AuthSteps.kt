package com.example.bankingService.test

import com.example.bankingService.entities.UserEntity
import com.example.bankingService.repositories.UsersRepository
import io.cucumber.java.en.Given
import io.cucumber.java.en.Then
import io.cucumber.java.en.When
import org.junit.jupiter.api.Assertions.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.*
import org.springframework.security.crypto.password.PasswordEncoder

class AuthSteps {

    @Autowired
    private lateinit var testRestTemplate: TestRestTemplate

    @Autowired
    private lateinit var usersRepository: UsersRepository

    @Autowired
    private lateinit var passwordEncoder: PasswordEncoder

    private var response: ResponseEntity<String>? = null

    @Given("A user with username {string} and password {string} exists")
    fun a_user_with_username_and_password_exists(username: String, password: String) {
        val encodedPassword = passwordEncoder.encode(password)
        val user = UserEntity(
            username = username,
            password = encodedPassword
        )
        usersRepository.save(user)
    }

    @When("I send a POST request for authentication to {string} with body")
    fun iSendPostRequestForAuth(endpoint: String, body: String) {
        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON
        response = testRestTemplate.exchange(endpoint, HttpMethod.POST, HttpEntity(body, headers), String::class.java)
    }

    @Then("the authentication response status should be {int}")
    fun theAuthenticationResponseStatusShouldBe(expectedStatus: Int) {
        assertEquals(expectedStatus, response?.statusCode?.value())
    }

    @Then("the authentication response body should not be empty")
    fun theAuthenticationResponseBodyShouldNotBeEmpty() {
        assertNotNull(response?.body)
        assertTrue(response?.body!!.isNotBlank())
    }
}
