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

    @Autowired 
    private AccountRepository accountRepository;
    
    @Autowired 
    private TransactionRepository transactionRepository;

    public int calculateHealthScore(String accountNumber) {
        Account account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        // ✅ Fixed: Matches your exact repository method name
        List<Transaction> transactions = transactionRepository.findBySourceAccountNumberOrTargetAccountNumberOrderByTimestampDesc(accountNumber, accountNumber);

        // Default score for new users
        if (account.getBalance() == 0 && transactions.isEmpty()) {
            return 50; 
        }

        int score = 0;

        // 1. Balance Stability (Max 40 points)
        if (account.getBalance() > 10000) {
            score += 40;
        } else if (account.getBalance() > 5000) {
            score += 30;
        } else if (account.getBalance() > 1000) {
            score += 15;
        }

        // 2. Transaction Consistency (Max 30 points)
        if (transactions.size() > 15) {
            score += 30;
        } else if (transactions.size() > 5) {
            score += 20;
        }

        // 3. Deposit/Savings Bonus (Max 30 points)
        // Rewards the "Add Funds" action
        long depositCount = transactions.stream()
                .filter(t -> "DEPOSIT".equalsIgnoreCase(t.getType()))
                .count();
        
        if (depositCount >= 3) {
            score += 30;
        } else if (depositCount >= 1) {
            score += 15;
        }

        // 4. Low Balance Penalty
        if (account.getBalance() < 500) {
            score -= 20;
        }

        // Ensure score stays within 0 - 100 range
        return Math.max(0, Math.min(score, 100));
    }
}