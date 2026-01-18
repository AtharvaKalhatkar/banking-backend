package com.smartcorebank.banking_backend.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String sourceAccountNumber;
    private String targetAccountNumber;
    private Double amount;
    private String type; // TRANSFER, DEPOSIT, WITHDRAW
    private LocalDateTime timestamp;

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getSourceAccountNumber() { return sourceAccountNumber; }
    public void setSourceAccountNumber(String s) { this.sourceAccountNumber = s; }
    public String getTargetAccountNumber() { return targetAccountNumber; }
    public void setTargetAccountNumber(String t) { this.targetAccountNumber = t; }
    public Double getAmount() { return amount; }
    public void setAmount(Double a) { this.amount = a; }
    public String getType() { return type; }
    public void setType(String t) { this.type = t; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime t) { this.timestamp = t; }
}