package com.bank.dto

import java.math.BigDecimal
import java.util.*

data class CreateAccountRequest(
    val customerId: UUID,
    val initialCredit: BigDecimal
)
