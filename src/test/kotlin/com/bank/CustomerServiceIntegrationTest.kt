package com.bank

import com.bank.service.CustomerService
import com.bank.model.Customer
import com.bank.repository.CustomerRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.util.*

@SpringBootTest
class CustomerServiceIntegrationTest(
    @Autowired val customerService: CustomerService,
    @Autowired val customerRepository: CustomerRepository
) {
    @Test
     fun `should return customer info with transactions`() {
        val customer = customerRepository.save(
         Customer(UUID.randomUUID(), "John", "Doe")
         )

         val response = customerService.getCustomerInfo(customer.id)

         assertThat(response.name).isEqualTo("John")
         assertThat(response.surname).isEqualTo("Doe")
        }
}