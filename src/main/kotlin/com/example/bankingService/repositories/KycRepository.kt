package com.example.bankingService.repositories

import com.example.bankingService.entities.KycEntity
import jakarta.inject.Named
import org.springframework.data.jpa.repository.JpaRepository

@Named
interface KycRepository : JpaRepository<KycEntity, Long>{
    fun findByUserId(userId: Long) : KycEntity?
}

