package com.example.bankingService.kyc

import com.example.bankingService.kyc.KycController.KycRequest
import com.example.bankingService.users.UsersRepository
import jakarta.inject.Named
import java.time.LocalDate

@Named
class KycService(
    private val kycRepository: KycRepository,
    private val usersRepository: UsersRepository
) {

    fun listKyc(): List<Kyc> = kycRepository.findAll().map {
        Kyc(
            userId = it.user.id,
            salary = it.salary,
            nationality = it.nationality,
            dateOfBirth = it.dateOfBirth
        )
    }

    fun createKyc(request: KycRequest): KycEntity {
        val user = usersRepository.findById(request.userId).get()
        val kycToBeSaved: KycEntity = kycRepository.findByUserId(request.userId)?.copy(
            user = user,
            dateOfBirth = request.dateOfBirth,
            nationality= request.nationality,
            salary= request.salary,
        ) ?: run {
            KycEntity(
                user = user,
                dateOfBirth = request.dateOfBirth,
                nationality= request.nationality,
                salary= request.salary,
            )
        }
        return kycRepository.save(kycToBeSaved)
    }
}

data class Kyc(
    val userId: Long?,
    var salary: Double,
    val nationality: String,
    val dateOfBirth: LocalDate
)