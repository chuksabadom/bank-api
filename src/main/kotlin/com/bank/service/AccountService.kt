package com.bank.service

import com.bank.repository.AccountRepository
import com.bank.exception.CustomerNotFoundException
import com.bank.model.Account
import com.bank.repository.CustomerRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal
import java.util.*

@Service
class AccountService(
    private val customerRepository: CustomerRepository,
    private val accountRepository: AccountRepository,
    private val transactionService: TransactionService
) {

    @Transactional
     fun createAccount(customerId: UUID, initialCredit: BigDecimal): Account {
         val customer = customerRepository.findById(customerId).orElseThrow { CustomerNotFoundException() }

         val account = accountRepository.save(
         Account(customerId = customer.id, balance = initialCredit)
         )

         if (initialCredit > BigDecimal.ZERO) {
             transactionService.createTransaction(account.id, initialCredit)
         }
        return account
     }
}