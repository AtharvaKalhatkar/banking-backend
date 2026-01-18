package com.smartcorebank.banking_backend.repository;

import com.smartcorebank.banking_backend.entity.LoanTracker;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface LoanTrackerRepository extends JpaRepository<LoanTracker, Long> {
    List<LoanTracker> findByAccountNumber(String accountNumber);
}