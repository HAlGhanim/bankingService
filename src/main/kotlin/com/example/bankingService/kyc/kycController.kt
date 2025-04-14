package com.example.bankingService.kyc


import com.example.bankingService.users.UsersService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDate

@RestController
class KycController(private val kycService: KycService, private val usersService: UsersService) {

    @GetMapping("/kyc/v1/list")
    fun kyc() = kycService.listKyc()

    @GetMapping("/kyc/v1/{userId}")
    fun kycById(@PathVariable userId: Long) = kycService.getKycById(userId)

    @PostMapping("/kyc/v1/create")
    fun createKyc(@RequestBody request: KycRequest): KycEntity {
        return kycService.createKyc(request)
    }

    data class KycRequest(
        val userId: Long,
        val salary: Double,
        val nationality: String,
        val dateOfBirth: LocalDate
    )
}