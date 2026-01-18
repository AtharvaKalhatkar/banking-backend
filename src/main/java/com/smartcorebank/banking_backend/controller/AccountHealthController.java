package com.smartcorebank.banking_backend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smartcorebank.banking_backend.service.AccountHealthService;

@RestController
@RequestMapping("/api/health")
public class AccountHealthController {

    private final AccountHealthService accountHealthService;

    public AccountHealthController(AccountHealthService accountHealthService) {
        this.accountHealthService = accountHealthService;
    }

    @GetMapping("/{accountNumber}")
    public int getHealthScore(@PathVariable String accountNumber) {
        return accountHealthService.calculateHealthScore(accountNumber);
    }
}
