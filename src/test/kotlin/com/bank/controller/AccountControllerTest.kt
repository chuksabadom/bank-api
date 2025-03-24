package com.bank.controller

import com.bank.service.AccountService
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.util.*

@WebMvcTest(AccountController::class)
class AccountControllerTest(@Autowired val mockMvc: MockMvc) {
     @MockBean
     lateinit var accountService: AccountService

     @Test
     fun `create account endpoint`() {
         val customerId = UUID.randomUUID()

         mockMvc.perform(
         post("/api/accounts")
         .contentType(MediaType.APPLICATION_JSON)
         .content("""
             {
             "customerId": "$customerId",
             "initialCredit": 100
             }
            """.trimIndent())
         ).andExpect(status().isOk)
     }
}