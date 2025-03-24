package com.bank.controller

import com.bank.dto.CustomerInfoResponse
import com.bank.dto.TransactionResponse
import com.bank.exception.CustomerNotFoundException
import com.bank.repository.CustomerRepository
import com.bank.service.CustomerService
import org.junit.jupiter.api.Test
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import java.math.BigDecimal
import java.time.Instant
import java.util.*

@WebMvcTest(CustomerController::class)
class CustomerControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    lateinit var customerService: CustomerService

    @MockBean
    lateinit var customerRepository: CustomerRepository

    @Test
    fun `should return customer info when customer exists`() {
        val customerId = UUID.randomUUID()
        val response = CustomerInfoResponse(
            name = "John",
            surname = "Doe",
            balance = BigDecimal("1000.00"),
            transactions = listOf(TransactionResponse(UUID.randomUUID(), BigDecimal( "100.00"), Instant.now()))
        )

        `when`(customerService.getCustomerInfo(customerId)).thenReturn(response)

        mockMvc.perform(get("/api/customers/$customerId"))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.name").value("John"))
            .andExpect(jsonPath("$.surname").value("Doe"))
            .andExpect(jsonPath("$.balance").value(1000.00))
            .andExpect(jsonPath("$.transactions[0].amount").value(100.00))
    }

    @Test
    fun `should return 404 when customer is not found`() {
        val customerId = UUID.randomUUID()
        `when`(customerService.getCustomerInfo(customerId)).thenThrow(CustomerNotFoundException())

        mockMvc.perform(get("/api/customers/$customerId"))
            .andExpect(status().isNotFound)
    }
}
