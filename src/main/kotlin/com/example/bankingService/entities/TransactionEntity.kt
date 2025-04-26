package com.example.bankingService.entities

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import java.math.BigDecimal

@Entity
@Table(name = "transactions")
data class TransactionsEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @ManyToOne
    @JoinColumn(name = "source_account")
    val sourceAccount: AccountEntity,

    @ManyToOne
    @JoinColumn(name = "destination_account")
    val destinationAccount: AccountEntity,

    val amount: BigDecimal
) {
    constructor() : this(null, AccountEntity(), AccountEntity(), BigDecimal.ZERO)
}
