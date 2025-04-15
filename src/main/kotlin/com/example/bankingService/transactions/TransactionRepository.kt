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
    @JoinColumn(name = "source_account")
    val sourceAccount: AccountEntity,

    @ManyToOne
    @JoinColumn(name = "destination_account")
    val destinationAccount: AccountEntity,

    val amount: Double
)