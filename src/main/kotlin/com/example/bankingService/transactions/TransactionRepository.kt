package com.example.bankingService.transactions

import com.example.bankingService.accounts.AccountEntity
import jakarta.inject.Named
import jakarta.persistence.*
import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDate

@Named
interface TransactionsRepository : JpaRepository<TransactionsEntity, Long>

@Entity
@Table(name = "transactions")
data class TransactionsEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @ManyToOne
    val sourceAccount: AccountEntity,

    @ManyToOne
    val destinationAccount: AccountEntity,

    val amount: Double,
){
    constructor(id: Long?) : this() {
        this.id = id
    }
    constructor() : this(null, AccountEntity(), AccountEntity(), 0.0)
}