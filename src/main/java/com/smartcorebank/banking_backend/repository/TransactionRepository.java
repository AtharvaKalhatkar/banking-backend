package com.smartcorebank.banking_backend.repository;

import com.smartcorebank.banking_backend.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    // ✅ 1. HISTORY (Needed by TransactionService & HealthService)
    List<Transaction> findBySourceAccountNumberOrTargetAccountNumberOrderByTimestampDesc(String sourceAccount, String targetAccount);

    // ✅ 2. CHATBOT: Total Spent (Fixes ChatBotService Error)
    @Query("SELECT SUM(t.amount) FROM Transaction t WHERE t.sourceAccountNumber = :accountNumber AND t.timestamp >= :startDate")
    Double calculateTotalSpent(@Param("accountNumber") String accountNumber, @Param("startDate") LocalDateTime startDate);

    // ✅ 3. CHATBOT: Spending Range (Fixes the Red Line in your Screenshot)
    @Query("SELECT SUM(t.amount) FROM Transaction t WHERE t.sourceAccountNumber = :accountNumber AND t.timestamp BETWEEN :startDate AND :endDate")
    Double calculateSpendingInRange(@Param("accountNumber") String accountNumber, @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

   // ✅ 4. PDF STATEMENT: Checks OLD (from/to) AND NEW (source/target) columns
    @Query("SELECT t FROM Transaction t WHERE " +
           "(t.sourceAccountNumber = :accountNumber OR t.targetAccountNumber = :accountNumber OR " +
           " t.fromAccountNumber = :accountNumber OR t.toAccountNumber = :accountNumber) " +
           "AND t.timestamp BETWEEN :startDate AND :endDate ORDER BY t.timestamp DESC")
    List<Transaction> findForStatement(@Param("accountNumber") String accountNumber, @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
}