package com.smartcorebank.banking_backend.dto;

public class AdminUserView {
    private String name;
    private String email;
    private String phoneNumber;
    private String accountNumber;
    private Double balance;
    private String accountStatus;

    // Constructor
    public AdminUserView(String name, String email, String phoneNumber, String accountNumber, Double balance, String accountStatus) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.accountStatus = accountStatus;
    }

    // Getters
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPhoneNumber() { return phoneNumber; }
    public String getAccountNumber() { return accountNumber; }
    public Double getBalance() { return balance; }
    public String getAccountStatus() { return accountStatus; }
}