package com.example.bankingService.kyc

import com.example.bankingService.users.UserEntity
import jakarta.inject.Named
import jakarta.persistence.*
import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDate

@Named
interface KycRepository : JpaRepository<KycEntity, Long>{
    fun findByUserId(userId: Long) : KycEntity?
}

@Entity
@Table(name = "kyc")
data class KycEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @OneToOne
    val user: UserEntity,

    val dateOfBirth: LocalDate,
    val nationality: String,
    var salary: Double,
){
    constructor(id: Long?) : this() {
        this.id = id
    }
    constructor() : this(null, UserEntity(), LocalDate.now()
        , "", 0.0)
}