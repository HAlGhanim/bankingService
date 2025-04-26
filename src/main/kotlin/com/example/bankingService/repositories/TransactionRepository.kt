    package com.example.bankingService.repositories


    import com.example.bankingService.entities.TransactionsEntity
    import jakarta.inject.Named
    import org.springframework.data.jpa.repository.JpaRepository


    @Named
    interface TransactionsRepository : JpaRepository<TransactionsEntity, Long>

