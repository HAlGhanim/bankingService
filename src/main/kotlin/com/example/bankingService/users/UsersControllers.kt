package com.example.bankingService.users


import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class UsersControllers(private val usersService: UsersService) {

    @GetMapping("/users/v1/list")
    fun users() = usersService.listUsers()

    @PostMapping("/users/v1/register")
    fun createUser(@RequestBody request: UserRequest): UserEntity {
        val user = UserEntity(
            username = request.username,
            password = request.password
        )
        return usersService.createUser(user)
    }

    data class UserRequest(
        val username: String,
        val password: String
    )
}