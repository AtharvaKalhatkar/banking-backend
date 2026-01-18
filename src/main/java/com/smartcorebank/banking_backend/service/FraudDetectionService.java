package com.smartcorebank.banking_backend.service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.smartcorebank.banking_backend.entity.Transaction;

@Service
public class FraudDetectionService {

    public String evaluateRisk(Transaction transaction,
                               List<Transaction> recentTransactions,
                               LocalDateTime accountCreatedAt) {

        if (transaction.getAmount() > 50000) {
            return "HIGH";
        }

        long recentCount = recentTransactions.stream()
                .filter(t -> Duration.between(t.getTimestamp(),
                        LocalDateTime.now()).toMinutes() < 1)
                .count();

        if (recentCount > 5) {
            return "HIGH";
        }

        if (Duration.between(accountCreatedAt, LocalDateTime.now()).toDays() < 7) {
            return "MEDIUM";
        }

        return "LOW";
    }
}
