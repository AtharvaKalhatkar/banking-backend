package com.smartcorebank.banking_backend.dto;

public class TransactionRequest {
    private String fromAccountNumber;
    private String toAccountNumber;
    private Double amount;

    // ✅ THESE GETTERS ARE REQUIRED
    public String getFromAccountNumber() { return fromAccountNumber; }
    public void setFromAccountNumber(String fromAccountNumber) { this.fromAccountNumber = fromAccountNumber; }

    public String getToAccountNumber() { return toAccountNumber; }
    public void setToAccountNumber(String toAccountNumber) { this.toAccountNumber = toAccountNumber; }

    public Double getAmount() { return amount; }
    public void setAmount(Double amount) { this.amount = amount; }
}