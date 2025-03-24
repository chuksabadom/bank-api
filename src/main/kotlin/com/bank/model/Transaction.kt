package com.bank.model

import jakarta.persistence.*
import java.math.BigDecimal
import java.time.Instant
import java.util.*

@Entity
@Table(name = "transaction")
data class Transaction(
    @Id
    @Column(columnDefinition = "VARCHAR(36)")
    val id: UUID = UUID.randomUUID(),

    @Column(nullable = false, columnDefinition = "VARCHAR(36)")
    val accountId: UUID,
    val amount: BigDecimal,
    val timestamp: Instant = Instant.now()
)
