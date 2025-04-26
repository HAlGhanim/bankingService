package com.example.bankingService.users


import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class UsersControllers(private val usersService: UsersService) {

    @GetMapping("/users/v1/list")
    fun users() = usersService.listUsers()

    @GetMapping("/users/v1/{userId}")
    fun user(@PathVariable userId: Long) = usersService.getUserDtoById(userId)

    @PostMapping("/users/v1/register")
    fun createUser(@RequestBody request: UserRequest) {
        val user = UserEntity(
            username = request.username,
            password = request.password
        )
        usersService.createUser(user)
    }

    data class UserRequest(
        val username: String,
        val password: String
    )


}