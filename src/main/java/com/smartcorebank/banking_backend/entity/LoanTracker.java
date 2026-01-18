package com.smartcorebank.banking_backend.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "loan_trackers")
public class LoanTracker {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String accountNumber;    // Link to our SmartCore User
    private String loanName;         // e.g., "SBI Home Loan"
    private String bankName;         // e.g., "State Bank of India"
    private Double principalAmount;  // e.g., 500000
    private Double interestRate;     // e.g., 8.5
    private Integer durationMonths;  // e.g., 60 (5 Years)
    private LocalDate startDate;     // e.g., 2024-01-01
    
    // We calculate this automatically, but store it just in case
    private Double emiAmount;

    // Constructors
    public LoanTracker() {}

    public LoanTracker(String accountNumber, String loanName, String bankName, Double principalAmount, Double interestRate, Integer durationMonths, LocalDate startDate) {
        this.accountNumber = accountNumber;
        this.loanName = loanName;
        this.bankName = bankName;
        this.principalAmount = principalAmount;
        this.interestRate = interestRate;
        this.durationMonths = durationMonths;
        this.startDate = startDate;
        this.emiAmount = calculateEMI(principalAmount, interestRate, durationMonths);
    }

    // EMI Formula: P * r * (1 + r)^n / ((1 + r)^n - 1)
    private Double calculateEMI(Double principal, Double annualRate, Integer months) {
        double monthlyRate = annualRate / 12 / 100;
        double emi = (principal * monthlyRate * Math.pow(1 + monthlyRate, months)) / 
                     (Math.pow(1 + monthlyRate, months) - 1);
        return Math.round(emi * 100.0) / 100.0; // Round to 2 decimals
    }

    // Getters and Setters (Generate these in IntelliJ: Right Click -> Generate -> Getters and Setters)
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getAccountNumber() { return accountNumber; }
    public void setAccountNumber(String accountNumber) { this.accountNumber = accountNumber; }
    public String getLoanName() { return loanName; }
    public void setLoanName(String loanName) { this.loanName = loanName; }
    public String getBankName() { return bankName; }
    public void setBankName(String bankName) { this.bankName = bankName; }
    public Double getPrincipalAmount() { return principalAmount; }
    public void setPrincipalAmount(Double principalAmount) { this.principalAmount = principalAmount; }
    public Double getInterestRate() { return interestRate; }
    public void setInterestRate(Double interestRate) { this.interestRate = interestRate; }
    public Integer getDurationMonths() { return durationMonths; }
    public void setDurationMonths(Integer durationMonths) { this.durationMonths = durationMonths; }
    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }
    public Double getEmiAmount() { return emiAmount; }
    public void setEmiAmount(Double emiAmount) { this.emiAmount = emiAmount; }
}