package com.bank.service

import com.bank.dto.TransactionResponse
import com.bank.repository.AccountRepository
import com.bank.repository.TransactionRepository
import com.bank.exception.AccountNotFoundException
import com.bank.model.Transaction
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal
import java.util.*

@Service
class TransactionService(
    private val accountRepository: AccountRepository,
    private val transactionRepository: TransactionRepository
) {
    @Transactional
    fun createTransaction(accountId: UUID, amount: BigDecimal): TransactionResponse {
        //val account =
        accountRepository.findById(accountId).orElseThrow { AccountNotFoundException() }

        val transaction = Transaction(
            accountId = accountId,
            amount = amount
        )

        val savedTransaction = transactionRepository.save(transaction)

        return TransactionResponse(
            accountId = savedTransaction.accountId,
            amount = savedTransaction.amount,
            timestamp = savedTransaction.timestamp
        )

    }

    @Transactional(readOnly = true)
    fun getAccountTransactions(accountId: UUID): List<TransactionResponse> {
        return transactionRepository.findByAccountId(accountId)
            .map { TransactionResponse(it.accountId, it.amount, it.timestamp) }
    }
}