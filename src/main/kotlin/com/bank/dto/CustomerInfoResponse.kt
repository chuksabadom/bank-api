package com.bank.dto

import java.math.BigDecimal

data class CustomerInfoResponse(
    val name: String,
    val surname: String,
    val balance: BigDecimal,
    val transactions: List<TransactionResponse>
)