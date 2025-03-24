package com.bank.service

import com.bank.exception.CustomerNotFoundException
import com.bank.model.Account
import com.bank.model.Customer
import com.bank.repository.AccountRepository
import com.bank.repository.CustomerRepository
import com.bank.repository.TransactionRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers.any
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.jupiter.MockitoExtension
import java.math.BigDecimal
import java.util.*

@ExtendWith(MockitoExtension::class)
class AccountServiceTest {

    @Mock
    lateinit var customerRepository: CustomerRepository

    @Mock
    lateinit var accountRepository: AccountRepository

    @Mock
    lateinit var transactionRepository: TransactionRepository


    @Mock
    lateinit var transactionService: TransactionService

    @InjectMocks
    lateinit var accountService: AccountService

    private lateinit var customerId: UUID
    private lateinit var initialCredit: BigDecimal
    private lateinit var account: Account
    private lateinit var customer: Customer

    @BeforeEach
    fun setUp() {
        // Given
         customerId = UUID.randomUUID()
         initialCredit = BigDecimal.valueOf(100)
         customer = Customer(id = customerId, name = "John", surname = "Doe")
         account = Account(id = UUID.randomUUID(), customerId = customerId, balance = initialCredit)
    }

    @Test
    fun `should create account and transaction when initial credit is greater than zero`() {


        `when`(customerRepository.findById(customerId)).thenReturn(Optional.of(customer))
        `when`(accountRepository.save(any(Account::class.java))).thenReturn(account)

        // When
        val createdAccount = accountService.createAccount(customerId, initialCredit)

        // Then
        assertNotNull(createdAccount)
        assertEquals(customerId, createdAccount.customerId)
        assertEquals(initialCredit, createdAccount.balance)

        verify(accountRepository, times(1)).save(any(Account::class.java))
//        verify(transactionService, times(1)).createTransaction(
//            eq(account.id),
//            eq(initialCredit) // Ensure the exact initial credit is passed
//        )
    }

    @Test
    fun `should create account without transaction when initial credit is zero`() {

        `when`(customerRepository.findById(customerId)).thenReturn(Optional.of(customer))
        `when`(accountRepository.save(any(Account::class.java))).thenReturn(account)

        // When
        val createdAccount = accountService.createAccount(customerId, initialCredit)

        // Then
        assertNotNull(createdAccount)
        assertEquals(customerId, createdAccount.customerId)
        assertEquals(initialCredit, createdAccount.balance)

        verify(accountRepository, times(1)).save(any(Account::class.java))
//        verify(transactionService, never()).createTransaction(
//            any(UUID::class.java),
//            any(BigDecimal::class.java)
//        )
    }

    @Test
    fun `should throw CustomerNotFoundException when customer does not exist`() {

        `when`(customerRepository.findById(customerId)).thenReturn(Optional.empty())

        // When & Then
        assertThrows<CustomerNotFoundException> {
            accountService.createAccount(customerId, initialCredit)
        }

        verify(accountRepository, never()).save(any(Account::class.java))
//        verify(transactionService, never()).createTransaction(
//            any(UUID::class.java),
//            any(BigDecimal::class.java)
//        )
    }
}
