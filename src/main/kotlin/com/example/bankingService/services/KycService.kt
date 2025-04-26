package com.example.bankingService.services

import com.example.bankingService.controllers.KycController.KycRequest
import com.example.bankingService.entities.KycEntity
import com.example.bankingService.repositories.KycRepository
import com.example.bankingService.repositories.UsersRepository
import jakarta.inject.Named
import java.math.BigDecimal
import java.time.LocalDate

@Named
class KycService(
    private val kycRepository: KycRepository,
    private val usersRepository: UsersRepository
) {

    fun listKyc(): List<Kyc> = kycRepository.findAll().map {
        Kyc(
            userId = it.user.id,
            firstName = it.firstName,
            lastName = it.lastName,
            dateOfBirth = it.dateOfBirth,
            salary = it.salary,
            nationality = it.nationality
        )
    }


    fun getKycById(userId: Long): Kyc {
        val kycEntity = kycRepository.findByUserId(userId)
            ?: throw NoSuchElementException("KYC not found for userId: $userId")

        return Kyc(
            userId = kycEntity.user.id,
            firstName = kycEntity.firstName,
            lastName = kycEntity.lastName,
            dateOfBirth = kycEntity.dateOfBirth,
            salary = kycEntity.salary,
            nationality = kycEntity.nationality
        )
    }

    fun createKyc(request: Kyc): KycEntity {
        val user = usersRepository.findById(request.userId!!)
            .orElseThrow { NoSuchElementException("User not found with ID: ${request.userId}") }

        val kycToBeSaved: KycEntity = kycRepository.findByUserId(request.userId)?.copy(
            firstName = request.firstName,
            lastName = request.lastName,
            nationality = request.nationality,
            dateOfBirth = request.dateOfBirth,
            salary = request.salary
        ) ?: run {
            KycEntity(
                user = user,
                firstName = request.firstName,
                lastName = request.lastName,
                nationality = request.nationality,
                dateOfBirth = request.dateOfBirth,
                salary = request.salary
            )
        }

        return kycRepository.save(kycToBeSaved)
    }

    data class Kyc(
        val userId: Long?,
        val firstName: String,
        val lastName: String,
        val nationality: String,
        val dateOfBirth: LocalDate,
        var salary: BigDecimal
    )
}

