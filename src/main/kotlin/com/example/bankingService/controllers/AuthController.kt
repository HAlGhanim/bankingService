package com.example.bankingService.controllers

import com.example.bankingService.authentication.jwt.JwtService
import com.example.bankingService.entities.UserEntity
import com.example.bankingService.services.UserResponseDto
import com.example.bankingService.services.UsersService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/auth")
class AuthController(
    private val authenticationManager: AuthenticationManager,
    private val userDetailsService: UserDetailsService,
    private val jwtService: JwtService,
    private val usersService: UsersService,
    private val passwordEncoder: PasswordEncoder
) {

    @PostMapping("/login")
    fun login(@RequestBody authRequest: AuthenticationRequest): String {
        val authToken = UsernamePasswordAuthenticationToken(authRequest.username, authRequest.password)
        val authentication = authenticationManager.authenticate(authToken)

        if (authentication.isAuthenticated) {
            val userDetails = userDetailsService.loadUserByUsername(authRequest.username)
            val token = jwtService.generateToken(userDetails.username)
            return token
        } else {
            throw UsernameNotFoundException("Invalid user request!")
        }
    }

    @PostMapping("/register")
    fun register(@RequestBody request: UserRequest): ResponseEntity<Void> {
        val user = UserEntity(
            username = request.username,
            password = passwordEncoder.encode(request.password)
        )
        usersService.createUser(user)
        return ResponseEntity.status(HttpStatus.CREATED).build()
    }

    @GetMapping("/users")
    fun listUsers() = mapOf("users" to usersService.listUsers())

    @GetMapping("/users/{userId}")
    fun getUserById(@PathVariable userId: Long): UserResponseDto {
        val user = usersService.getUserById(userId)
        return UserResponseDto(
            id = user.id,
            username = user.username
        )
    }

    data class AuthenticationRequest(
        val username: String,
        val password: String
    )

    data class UserRequest(
        val username: String,
        val password: String
    )
}
