package com.smartcorebank.banking_backend.repository;

import com.smartcorebank.banking_backend.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {
    
    Optional<Account> findByAccountNumber(String accountNumber);
    
    // For Admin Dashboard (Finding the account object)
    Optional<Account> findByUserId(Long userId);

    // FIX: Added this boolean check for AccountService
    boolean existsByUserId(Long userId);
}