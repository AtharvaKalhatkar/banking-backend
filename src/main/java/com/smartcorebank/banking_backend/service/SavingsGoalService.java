package com.smartcorebank.banking_backend.service;

import com.smartcorebank.banking_backend.entity.Account;
import com.smartcorebank.banking_backend.entity.SavingsGoal;
import com.smartcorebank.banking_backend.repository.AccountRepository;
import com.smartcorebank.banking_backend.repository.SavingsGoalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SavingsGoalService {

    @Autowired
    private SavingsGoalRepository savingsGoalRepository;

    @Autowired
    private AccountRepository accountRepository;

    // 1. Create a new Goal
    public SavingsGoal createGoal(String accountNumber, String goalName, Double targetAmount) {
        SavingsGoal goal = new SavingsGoal(accountNumber, goalName, targetAmount);
        return savingsGoalRepository.save(goal);
    }

    // 2. Add Money to Goal (Lock it!)
    @Transactional
    public SavingsGoal addFunds(Long goalId, Double amount) {
        SavingsGoal goal = savingsGoalRepository.findById(goalId)
                .orElseThrow(() -> new RuntimeException("Goal not found"));

        Account account = accountRepository.findByAccountNumber(goal.getAccountNumber())
                .orElseThrow(() -> new RuntimeException("Account not found"));

        if (account.getBalance() < amount) {
            throw new RuntimeException("Insufficient funds in main account");
        }

        // Deduct from Main Account
        account.setBalance(account.getBalance() - amount);
        
        // Add to Goal
        goal.setCurrentAmount(goal.getCurrentAmount() + amount);

        // Check if completed
        if (goal.getCurrentAmount() >= goal.getTargetAmount()) {
            goal.setStatus("COMPLETED");
        }

        accountRepository.save(account);
        return savingsGoalRepository.save(goal);
    }

    // 3. View my Goals
    public List<SavingsGoal> getMyGoals(String accountNumber) {
        return savingsGoalRepository.findByAccountNumber(accountNumber);
    }
}