package com.smartcorebank.banking_backend.repository;

import com.smartcorebank.banking_backend.entity.SavingsGoal;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SavingsGoalRepository extends JpaRepository<SavingsGoal, Long> {
    
    // Find all goals for a specific user (e.g., Atharva's goals)
    List<SavingsGoal> findByAccountNumber(String accountNumber);
}