package com.bank.repository

import com.bank.model.Account
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import java.util.*

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class AccountRepositoryTest(
     @Autowired val repo: AccountRepository
) {
     @Test
     fun `should save and retrieve account`() {
         val customerId = UUID.randomUUID()
         repo.save(Account(customerId = customerId))

         assertThat(repo.findByCustomerId(customerId)).hasSize(1)
         }
}