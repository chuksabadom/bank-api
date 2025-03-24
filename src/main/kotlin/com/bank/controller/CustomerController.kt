package com.bank.controller

import com.bank.dto.CustomerInfoResponse
import com.bank.model.Customer
import com.bank.repository.CustomerRepository
import com.bank.service.CustomerService
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@Tag(name = "Customer", description = "API for getting customer info")
@RestController
@RequestMapping("/api/v1/customers")
class CustomerController(
    private val customerService: CustomerService,
    private val customerRepository: CustomerRepository
) {
    @GetMapping
    fun getAllCustomers(): List<Customer> {
        return customerRepository.findAll()
    }

    @GetMapping("/{id}")
    fun getCustomerById(@PathVariable id: UUID): ResponseEntity<CustomerInfoResponse> {
       val customer = customerService.getCustomerInfo(id)
    return ResponseEntity.ok(customer)
    }
}
