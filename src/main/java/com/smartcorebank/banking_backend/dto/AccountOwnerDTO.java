package com.smartcorebank.banking_backend.dto;

public class AccountOwnerDTO {
    private String ownerName;
    private String email;
    private String accountNumber;

    public AccountOwnerDTO(String ownerName, String email, String accountNumber) {
        this.ownerName = ownerName;
        this.email = email;
        this.accountNumber = accountNumber;
    }

    // Getters
    public String getOwnerName() { return ownerName; }
    public String getEmail() { return email; }
    public String getAccountNumber() { return accountNumber; }
}