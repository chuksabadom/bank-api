package com.bank.dto

import java.math.BigDecimal
import java.time.Instant
import java.util.*

data class TransactionResponse(
    var accountId: UUID,
    val amount: BigDecimal,
    val timestamp: Instant
)
