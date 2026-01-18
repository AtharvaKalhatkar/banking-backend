package com.smartcorebank.banking_backend.controller;

import com.smartcorebank.banking_backend.dto.AdminUserView;
import com.smartcorebank.banking_backend.entity.Account;
import com.smartcorebank.banking_backend.entity.User;
import com.smartcorebank.banking_backend.repository.AccountRepository;
import com.smartcorebank.banking_backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final UserRepository userRepository;
    private final AccountRepository accountRepository;

    @Autowired
    public AdminController(UserRepository userRepository, AccountRepository accountRepository) {
        this.userRepository = userRepository;
        this.accountRepository = accountRepository;
    }

    // ... existing imports and dashboard method ...

    // NEW: Freeze or Unfreeze an Account
    @PutMapping("/accounts/{userId}/status")
    public ResponseEntity<?> updateAccountStatus(@PathVariable Long userId, @RequestParam String status) {
        Account account = accountRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Account not found for this user"));
        
        // Only allow valid statuses
        if (!status.equalsIgnoreCase("ACTIVE") && !status.equalsIgnoreCase("FROZEN")) {
            return ResponseEntity.badRequest().body("Invalid status. Use ACTIVE or FROZEN.");
        }

        account.setStatus(status.toUpperCase());
        accountRepository.save(account);
        
        return ResponseEntity.ok(Map.of("message", "Account status updated to " + status.toUpperCase()));
    }
    

    // NEW: Combined Dashboard View
    @GetMapping("/dashboard")
    public ResponseEntity<List<AdminUserView>> getAdminDashboard() {
        List<User> users = userRepository.findAll();
        List<AdminUserView> dashboardData = new ArrayList<>();

        for (User user : users) {
            // Find account for this user (if it exists)
            Optional<Account> accountOpt = accountRepository.findByUserId(user.getId());
            
            String accNo = "N/A";
            Double balance = 0.0;
            String status = "NO ACCOUNT";

            if (accountOpt.isPresent()) {
                Account acc = accountOpt.get();
                accNo = acc.getAccountNumber();
                balance = acc.getBalance();
                status = acc.getStatus() != null ? acc.getStatus() : "ACTIVE";
            }

            dashboardData.add(new AdminUserView(
                user.getName(),
                user.getEmail(),
                user.getPhoneNumber() != null ? user.getPhoneNumber() : "N/A",
                accNo,
                balance,
                status
            ));
        }

        return ResponseEntity.ok(dashboardData);
    }
}