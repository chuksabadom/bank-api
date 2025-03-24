package com.bank.controller

import com.bank.dto.CreateAccountRequest
import com.bank.model.Account
import com.bank.service.AccountService
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "Accounts", description = "API for managing accounts")
@RestController
@RequestMapping("/api/accounts")
class AccountController(
      private val accountService: AccountService
){

    @PostMapping
    fun createAccount(
      @Valid @RequestBody request: CreateAccountRequest
    ): ResponseEntity<Account> {
        val account = accountService.createAccount(request.customerId, request.initialCredit)
        return ResponseEntity.ok(account)
    }
}