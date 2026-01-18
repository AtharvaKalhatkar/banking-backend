package com.smartcorebank.banking_backend.service;

import com.smartcorebank.banking_backend.dto.TransactionRequest;
import com.smartcorebank.banking_backend.entity.Account;
import com.smartcorebank.banking_backend.entity.Transaction;
import com.smartcorebank.banking_backend.repository.AccountRepository;
import com.smartcorebank.banking_backend.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class TransactionService {

    @Autowired private AccountRepository accountRepository;
    @Autowired private TransactionRepository transactionRepository;

    @Transactional
    public String transferFunds(TransactionRequest request) {
        // 1. Validate Accounts exist
        Account from = accountRepository.findByAccountNumber(request.getFromAccountNumber())
                .orElseThrow(() -> new RuntimeException("Sender account not found"));
        Account to = accountRepository.findByAccountNumber(request.getToAccountNumber())
                .orElseThrow(() -> new RuntimeException("Receiver account not found"));

        // 2. Check Balance
        if (from.getBalance() < request.getAmount()) {
            throw new RuntimeException("Insufficient Funds");
        }

        // 3. Move Money
        from.setBalance(from.getBalance() - request.getAmount());
        to.setBalance(to.getBalance() + request.getAmount());
        
        accountRepository.save(from);
        accountRepository.save(to);

        // 4. Save Transaction (Mapping Request -> Entity)
        Transaction t = new Transaction();
        t.setSourceAccountNumber(request.getFromAccountNumber()); // Correct mapping
        t.setTargetAccountNumber(request.getToAccountNumber());   // Correct mapping
        t.setAmount(request.getAmount());
        t.setType("TRANSFER");
        t.setTimestamp(LocalDateTime.now());
        
        transactionRepository.save(t);

        return "Success";
    }

    // Support methods for Controller
    public Transaction deposit(String accNum, Double amount) {
        Account acc = accountRepository.findByAccountNumber(accNum).orElseThrow();
        acc.setBalance(acc.getBalance() + amount);
        accountRepository.save(acc);
        
        Transaction t = new Transaction();
        t.setSourceAccountNumber("ATM");
        t.setTargetAccountNumber(accNum);
        t.setAmount(amount);
        t.setType("DEPOSIT");
        t.setTimestamp(LocalDateTime.now());
        return transactionRepository.save(t);
    }

    public Transaction withdraw(String accNum, Double amount) {
        Account acc = accountRepository.findByAccountNumber(accNum).orElseThrow();
        if(acc.getBalance() < amount) throw new RuntimeException("Insufficient Funds");
        acc.setBalance(acc.getBalance() - amount);
        accountRepository.save(acc);

        Transaction t = new Transaction();
        t.setSourceAccountNumber(accNum);
        t.setTargetAccountNumber("ATM");
        t.setAmount(amount);
        t.setType("WITHDRAW");
        t.setTimestamp(LocalDateTime.now());
        return transactionRepository.save(t);
    }

    public List<Transaction> getTransactionHistory(String accNum) {
        return transactionRepository.findBySourceAccountNumberOrTargetAccountNumberOrderByTimestampDesc(accNum, accNum);
    }
}
