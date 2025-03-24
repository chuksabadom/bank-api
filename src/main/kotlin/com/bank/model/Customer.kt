package com.bank.model

import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "customer")
data class Customer(
    @Id
    @Column(columnDefinition = "VARCHAR(36)")
    val id: UUID = UUID.randomUUID(),
    val name: String,
    val surname: String
)
