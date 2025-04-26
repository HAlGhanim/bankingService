package com.example.bankingService.controllers

import com.example.bankingService.services.KycService
import com.example.bankingService.services.UsersService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.math.BigDecimal
import java.time.LocalDate

@RestController
@RequestMapping("/kyc/v1")
class KycController(
    private val kycService: KycService,
    private val usersService: UsersService
) {

    @GetMapping("/list")
    fun kyc() = mapOf(
        "kyc" to kycService.listKyc()
    )

    @GetMapping("/{userId}")
    fun kycById(@PathVariable userId: Long) = kycService.getKycById(userId)

    @PostMapping("/create")
    fun createKyc(@RequestBody request: KycRequest): KycService.Kyc {
        val kyc = KycService.Kyc(
            userId = request.userId,
            firstName = request.firstName,
            lastName = request.lastName,
            nationality = request.nationality,
            dateOfBirth = request.dateOfBirth,
            salary = request.salary
        )
        val createdEntity = kycService.createKyc(kyc)
        return KycService.Kyc(
            userId = createdEntity.user.id,
            firstName = createdEntity.firstName,
            lastName = createdEntity.lastName,
            nationality = createdEntity.nationality,
            dateOfBirth = createdEntity.dateOfBirth,
            salary = createdEntity.salary
        )
    }

    data class KycRequest(
        val userId: Long,
        val firstName: String,
        val lastName: String,
        val nationality: String,
        val dateOfBirth: LocalDate,
        val salary: BigDecimal
    )
}
