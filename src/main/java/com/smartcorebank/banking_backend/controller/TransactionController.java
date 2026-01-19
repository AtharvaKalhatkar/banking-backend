package com.smartcorebank.banking_backend.controller;

import com.smartcorebank.banking_backend.dto.TransactionRequest;
import com.smartcorebank.banking_backend.entity.Transaction;
import com.smartcorebank.banking_backend.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    // 1. Transfer money between two existing accounts
    @PostMapping("/transfer")
    public ResponseEntity<String> transfer(@RequestBody TransactionRequest request) {
        return ResponseEntity.ok(transactionService.transferFunds(request));
    }

    // 2. Add funds to an account (Triggers Health Score bonus)
    @PostMapping("/deposit")
    public ResponseEntity<String> deposit(@RequestParam String accountNumber, @RequestParam Double amount) {
        // Calling the consolidated method in TransactionService
        transactionService.depositFunds(accountNumber, amount);
        return ResponseEntity.ok("Successfully deposited ₹" + String.format("%.2f", amount) + " to account: " + accountNumber);
    }

    // 3. Remove funds from an account
    @PostMapping("/withdraw")
    public ResponseEntity<String> withdraw(@RequestParam String accountNumber, @RequestParam Double amount) {
        transactionService.withdraw(accountNumber, amount);
        return ResponseEntity.ok("Successfully withdrawn ₹" + String.format("%.2f", amount) + " from account: " + accountNumber);
    }

    // 4. Get all transactions (Inbound and Outbound)
    @GetMapping("/history/{accountNumber}")
    public ResponseEntity<List<Transaction>> getHistory(@PathVariable String accountNumber) {
        return ResponseEntity.ok(transactionService.getTransactionHistory(accountNumber));
    }
}