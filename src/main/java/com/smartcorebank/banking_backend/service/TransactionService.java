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
        Account from = accountRepository.findByAccountNumber(request.getFromAccountNumber())
                .orElseThrow(() -> new RuntimeException("Sender account not found"));
        Account to = accountRepository.findByAccountNumber(request.getToAccountNumber())
                .orElseThrow(() -> new RuntimeException("Receiver account not found"));

        if (from.getBalance() < request.getAmount()) {
            throw new RuntimeException("Insufficient Funds");
        }

        from.setBalance(from.getBalance() - request.getAmount());
        to.setBalance(to.getBalance() + request.getAmount());
        
        accountRepository.save(from);
        accountRepository.save(to);

        Transaction t = new Transaction();
        t.setSourceAccountNumber(request.getFromAccountNumber());
        t.setTargetAccountNumber(request.getToAccountNumber());
        t.setAmount(request.getAmount());
        t.setType("TRANSFER");
        t.setTimestamp(LocalDateTime.now());
        
        transactionRepository.save(t);
        return "Success";
    }

    // ✅ Consolidated Deposit Method
    // This provides the data your Health Score and PDF Statement need.
    @Transactional
    public Transaction depositFunds(String accountNumber, Double amount) {
        Account account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        account.setBalance(account.getBalance() + amount);
        accountRepository.save(account);

        Transaction deposit = new Transaction();
        deposit.setSourceAccountNumber("CASH-DEPOSIT"); 
        deposit.setTargetAccountNumber(accountNumber);
        deposit.setAmount(amount);
        deposit.setType("DEPOSIT"); // Rewards bonus points in AccountHealthService
        deposit.setTimestamp(LocalDateTime.now());

        return transactionRepository.save(deposit);
    }

    @Transactional
    public Transaction withdraw(String accNum, Double amount) {
        Account acc = accountRepository.findByAccountNumber(accNum).orElseThrow();
        if(acc.getBalance() < amount) throw new RuntimeException("Insufficient Funds");
        
        acc.setBalance(acc.getBalance() - amount);
        accountRepository.save(acc);

        Transaction t = new Transaction();
        t.setSourceAccountNumber(accNum);
        t.setTargetAccountNumber("ATM-WITHDRAWAL");
        t.setAmount(amount);
        t.setType("WITHDRAW");
        t.setTimestamp(LocalDateTime.now());
        
        return transactionRepository.save(t);
    }

    public List<Transaction> getTransactionHistory(String accNum) {
        // Uses the bidirectional query we fixed in your TransactionRepository
        return transactionRepository.findBySourceAccountNumberOrTargetAccountNumberOrderByTimestampDesc(accNum, accNum);
    }
}