package com.smartcorebank.banking_backend.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "savings_goals")
public class SavingsGoal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String accountNumber;
    private String goalName;      // e.g., "Goa Trip"
    private Double targetAmount;  // e.g., 10000.00
    private Double currentAmount; // e.g., 2000.00
    private String status;        // "IN_PROGRESS" or "COMPLETED"
    private LocalDateTime createdDate;

    // Constructors
    public SavingsGoal() {}

    public SavingsGoal(String accountNumber, String goalName, Double targetAmount) {
        this.accountNumber = accountNumber;
        this.goalName = goalName;
        this.targetAmount = targetAmount;
        this.currentAmount = 0.0;
        this.status = "IN_PROGRESS";
        this.createdDate = LocalDateTime.now();
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getAccountNumber() { return accountNumber; }
    public void setAccountNumber(String accountNumber) { this.accountNumber = accountNumber; }

    public String getGoalName() { return goalName; }
    public void setGoalName(String goalName) { this.goalName = goalName; }

    public Double getTargetAmount() { return targetAmount; }
    public void setTargetAmount(Double targetAmount) { this.targetAmount = targetAmount; }

    public Double getCurrentAmount() { return currentAmount; }
    public void setCurrentAmount(Double currentAmount) { this.currentAmount = currentAmount; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}