package com.smartcorebank.banking_backend.controller;

import com.smartcorebank.banking_backend.entity.Account;
import com.smartcorebank.banking_backend.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;

    // ✅ 1. CREATE ACCOUNT (Cleaner input)
    @PostMapping
    public ResponseEntity<Account> createAccount(@RequestBody Map<String, Object> request) {
        // We ONLY ask for userId, balance, and status now.
        // Account Number is generated automatically.
        
        Long userId = Long.valueOf(request.get("userId").toString());
        Double balance = Double.valueOf(request.get("balance").toString());
        String status = (String) request.get("status");

        Account account = accountService.createAccount(userId, balance, status);
        
        return ResponseEntity.ok(account);
    }

    // 2. GET BALANCE
    @GetMapping("/balance/{accountNumber}")
    public ResponseEntity<Double> getBalance(@PathVariable String accountNumber) {
        Double balance = accountService.getBalance(accountNumber);
        return ResponseEntity.ok(balance);
    }

    // 3. SEARCH ACCOUNT
    @GetMapping("/search/{accountNumber}")
    public ResponseEntity<Account> getAccount(@PathVariable String accountNumber) {
        try {
            Account account = accountService.getAccount(accountNumber); 
            return ResponseEntity.ok(account);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}