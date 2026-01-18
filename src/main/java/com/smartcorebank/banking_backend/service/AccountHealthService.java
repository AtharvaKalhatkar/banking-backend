package com.smartcorebank.banking_backend.service;

import com.smartcorebank.banking_backend.entity.Account;
import com.smartcorebank.banking_backend.entity.Transaction;
import com.smartcorebank.banking_backend.repository.AccountRepository;
import com.smartcorebank.banking_backend.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AccountHealthService {

    @Autowired private AccountRepository accountRepository;
    @Autowired private TransactionRepository transactionRepository;

    public int calculateHealthScore(String accountNumber) {
        Account account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        // ✅ Calls the correct method from TransactionRepository
        List<Transaction> transactions = transactionRepository.findBySourceAccountNumberOrTargetAccountNumberOrderByTimestampDesc(accountNumber, accountNumber);

        if (account.getBalance() == 0 && transactions.isEmpty()) {
            return 50; // Default score
        }

        int score = 0;
        if (account.getBalance() > 5000) score += 40;
        if (account.getBalance() > 1000) score += 20;
        if (transactions.size() > 5) score += 30;
        if (transactions.size() > 10) score += 10;

        return Math.min(score, 100);
    }
}