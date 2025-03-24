package com.bank.repository

import com.bank.model.Transaction
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import java.math.BigDecimal
import java.util.*

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class TransactionRepositoryTest(
     @Autowired val repo: TransactionRepository
) {
     @Test
     fun `should save and retrieve account`() {
         val accountId = UUID.randomUUID()
         repo.save(Transaction(accountId = accountId, amount = BigDecimal(100)))

         assertThat(repo.findByAccountId(accountId)).hasSize(1)
         }
}