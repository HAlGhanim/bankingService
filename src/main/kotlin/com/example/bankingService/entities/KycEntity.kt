package com.example.bankingService.entities

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToOne
import jakarta.persistence.Table
import java.math.BigDecimal
import java.time.LocalDate

@Entity
@Table(name = "kyc")
data class KycEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @OneToOne
    val user: UserEntity,

    val firstName: String,
    val lastName: String,
    val nationality: String,
    val dateOfBirth: LocalDate,
    var salary: BigDecimal
) {
    constructor() : this(null, UserEntity(), "", "", "", LocalDate.now(), BigDecimal.ZERO)
    constructor(id: Long?) : this(id, UserEntity(), "", "", "", LocalDate.now(), BigDecimal.ZERO)
}
