package com.bank.repository

import com.bank.model.Transaction
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface TransactionRepository : JpaRepository<Transaction, UUID> {
     fun findByAccountId(accountId: UUID): List<Transaction>
}