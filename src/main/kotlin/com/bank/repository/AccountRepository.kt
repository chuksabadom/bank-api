package com.bank.repository

import com.bank.model.Account
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface AccountRepository : JpaRepository<Account, UUID> {
     fun findByCustomerId(customerId: UUID): List<Account>
}