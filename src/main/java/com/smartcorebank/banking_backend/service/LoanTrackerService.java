package com.smartcorebank.banking_backend.service;

import com.smartcorebank.banking_backend.entity.LoanTracker;
import com.smartcorebank.banking_backend.repository.LoanTrackerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class LoanTrackerService {

    @Autowired
    private LoanTrackerRepository loanTrackerRepository;

    public LoanTracker addLoan(String accountNumber, String loanName, String bankName, 
                               Double amount, Double rate, Integer months, String startDateStr) {
        
        LocalDate start = LocalDate.parse(startDateStr);
        LoanTracker loan = new LoanTracker(accountNumber, loanName, bankName, amount, rate, months, start);
        return loanTrackerRepository.save(loan);
    }

    public List<LoanTracker> getMyLoans(String accountNumber) {
        return loanTrackerRepository.findByAccountNumber(accountNumber);
    }
}