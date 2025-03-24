package com.bank.service

import com.bank.dto.CustomerInfoResponse
import com.bank.exception.CustomerNotFoundException
import com.bank.repository.AccountRepository
import com.bank.repository.CustomerRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class CustomerService(
    private val customerRepository: CustomerRepository,
    private val accountRepository: AccountRepository,
    private val transactionService: TransactionService
) {
     fun getCustomerInfo(customerId: UUID): CustomerInfoResponse {
         val customer = customerRepository.findById(customerId)
         .orElseThrow { CustomerNotFoundException() }

         val accounts = accountRepository.findByCustomerId(customerId)
         val transactions = accounts.flatMap { transactionService.getAccountTransactions(it.id) }

         return CustomerInfoResponse(
            name = customer.name,
            surname = customer.surname,
            balance = accounts.sumOf { it.balance },
            transactions = transactions
         )
     }
}