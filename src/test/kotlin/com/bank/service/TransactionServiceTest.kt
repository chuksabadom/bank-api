package com.bank.service

import com.bank.exception.AccountNotFoundException
import com.bank.model.Account
import com.bank.model.Transaction
import com.bank.repository.AccountRepository
import com.bank.repository.TransactionRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentCaptor
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.jupiter.MockitoExtension
import java.math.BigDecimal
import java.time.Instant
import java.util.*
import org.mockito.ArgumentMatchers.any
import org.mockito.Captor

@ExtendWith(MockitoExtension::class)
class TransactionServiceTest {

    @Mock
    private lateinit var accountRepository: AccountRepository

    @Mock
    private lateinit var transactionRepository: TransactionRepository

    @InjectMocks
    private lateinit var transactionService: TransactionService

    @Captor
    private lateinit var transactionCaptor: ArgumentCaptor<Transaction>

    private lateinit var accountId: UUID
    private lateinit var transaction: Transaction

    @BeforeEach
    fun setUp() {
        accountId = UUID.randomUUID()
        transaction = Transaction(
            accountId = accountId,
            amount = BigDecimal("100.50"),
            timestamp = Instant.now()
        )
    }

    @Test
    fun `test createTransaction should save transaction and return response`() {

        val account = Account(id = accountId, customerId = UUID.randomUUID(), BigDecimal("500.00"))
        `when`(accountRepository.findById(accountId)).thenReturn(Optional.of(account))
        `when`(transactionRepository.save(any(Transaction::class.java))).thenAnswer { it.arguments[0] }

        val response = transactionService.createTransaction(accountId, BigDecimal("100.50"))

        assertNotNull(response)
        assertEquals(accountId, response.accountId)
        assertEquals(BigDecimal("100.50"), response.amount)

        verify(transactionRepository).save(transactionCaptor.capture())
    }

    @Test
    fun `test getAccountTransactions should return list of transactions`() {

        `when`(transactionRepository.findByAccountId(accountId)).thenReturn(listOf(transaction))

        val response = transactionService.getAccountTransactions(accountId)

        assertNotNull(response)
        assertEquals(1, response.size)
        assertEquals(BigDecimal("100.50"), response[0].amount)

        verify(transactionRepository).findByAccountId(accountId)
    }

    @Test
    fun `test createTransaction should throw exception if account does not exist`() {

        `when`(accountRepository.findById(accountId)).thenReturn(Optional.empty())

        assertThrows(AccountNotFoundException::class.java) {
            transactionService.createTransaction(accountId, BigDecimal("50.00"))
        }
    }
}
