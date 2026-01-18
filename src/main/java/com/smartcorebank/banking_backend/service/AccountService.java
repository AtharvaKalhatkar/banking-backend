package com.smartcorebank.banking_backend.service;

import com.smartcorebank.banking_backend.entity.Account;
import com.smartcorebank.banking_backend.entity.User;
import com.smartcorebank.banking_backend.repository.AccountRepository;
import com.smartcorebank.banking_backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private UserRepository userRepository;

    // ✅ 1. CREATE ACCOUNT (Now with Auto-Generation!)
    public Account createAccount(Long userId, Double balance, String status) {
        // Find User
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));

        Account account = new Account();
        account.setUser(user);

        // 🔥 AUTO-GENERATE 12-DIGIT NUMBER
        String uniqueAccountNumber = generateUniqueAccountNumber();
        account.setAccountNumber(uniqueAccountNumber);

        account.setBalance(balance);
        account.setStatus(status);
        account.setSmartSavings(0.0);

        return accountRepository.save(account);
    }

    // 🔄 Helper Method: Generates 12 digits and checks if they exist
    private String generateUniqueAccountNumber() {
        String accountNumber;
        Random random = new Random();
        do {
            // Generate a random 12-digit number
            long number = 100000000000L + (long)(random.nextDouble() * 900000000000L);
            accountNumber = String.valueOf(number);
            
        // Keep generating if (by minimal chance) it already exists
        } while (accountRepository.findByAccountNumber(accountNumber).isPresent());
        
        return accountNumber;
    }

    // 2. GET ACCOUNT
    public Account getAccount(String accountNumber) {
        return accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new RuntimeException("Account not found"));
    }

    // 3. GET BALANCE
    public Double getBalance(String accountNumber) {
        return getAccount(accountNumber).getBalance();
    }
}