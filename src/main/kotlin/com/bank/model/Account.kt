package com.bank.model

import jakarta.persistence.*
import java.math.BigDecimal
import java.util.*

@Entity
@Table(name = "account")
data class Account(
    @Id
    @Column(columnDefinition = "VARCHAR(36)")
    val id: UUID = UUID.randomUUID(),

    @Column(nullable = false, columnDefinition = "VARCHAR(36)")
    val customerId: UUID,

    val balance: BigDecimal = BigDecimal.ZERO
)
