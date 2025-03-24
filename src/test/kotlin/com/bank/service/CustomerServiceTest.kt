package com.bank.service

import com.bank.dto.TransactionResponse
import com.bank.exception.CustomerNotFoundException
import com.bank.model.Account
import com.bank.model.Customer
import com.bank.repository.AccountRepository
import com.bank.repository.CustomerRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.jupiter.MockitoExtension
import java.math.BigDecimal
import java.time.Instant
import java.util.*



@ExtendWith(MockitoExtension::class)
class CustomerServiceTest {

    @Mock
    lateinit var customerRepository: CustomerRepository

    @Mock
    lateinit var accountRepository: AccountRepository

    @Mock
    lateinit var transactionService: TransactionService

    @InjectMocks
    lateinit var customerService: CustomerService

    @Test
    fun `test getCustomerInfo when customer exists`() {
        // Create mock data
        val customerId = UUID.randomUUID()
        val customer = Customer(id = customerId, name = "John", surname = "Doe")
        val account1 = Account(id = UUID.randomUUID(), customerId = customerId, balance = BigDecimal(100.0))
        val account2 = Account(id = UUID.randomUUID(), customerId = customerId, balance = BigDecimal(200.0))
        val transaction1 = TransactionResponse(accountId = account1.id, amount = BigDecimal(50.0), Instant.now())
        val transaction2 = TransactionResponse(accountId = account2.id, amount = BigDecimal(150.0), Instant.now())

        // Mocking the repository and service methods
        `when`(customerRepository.findById(customerId)).thenReturn(Optional.of(customer))
        `when`(accountRepository.findByCustomerId(customerId)).thenReturn(listOf(account1, account2))
        `when`(transactionService.getAccountTransactions(account1.id)).thenReturn(listOf(transaction1))
        `when`(transactionService.getAccountTransactions(account2.id)).thenReturn(listOf(transaction2))

        // Call the service method
        val customerInfoResponse = customerService.getCustomerInfo(customerId)

        // Verify the results
        assertNotNull(customerInfoResponse)
        assertEquals("John", customerInfoResponse.name)
        assertEquals("Doe", customerInfoResponse.surname)
        assertEquals(BigDecimal(300.0), customerInfoResponse.balance)
        assertEquals(2, customerInfoResponse.transactions.size)
    }

    @Test
    fun `test getCustomerInfo when customer not found`() {
        // Setup a customer ID that doesn't exist in the repository
        val customerId = UUID.randomUUID()
        `when`(customerRepository.findById(customerId)).thenReturn(Optional.empty())

        // Verify that a CustomerNotFoundException is thrown
        assertThrows(CustomerNotFoundException::class.java) {
            customerService.getCustomerInfo(customerId)
        }
    }
}
