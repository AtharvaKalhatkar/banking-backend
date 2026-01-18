package com.smartcorebank.banking_backend.controller;

import com.smartcorebank.banking_backend.entity.LoanTracker;
import com.smartcorebank.banking_backend.service.LoanTrackerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/loans")
public class LoanTrackerController {

    @Autowired
    private LoanTrackerService loanTrackerService;

    @PostMapping("/add")
    public ResponseEntity<LoanTracker> addLoan(@RequestBody Map<String, Object> request) {
        String accountNumber = (String) request.get("accountNumber");
        String loanName = (String) request.get("loanName");
        String bankName = (String) request.get("bankName");
        Double amount = Double.valueOf(request.get("amount").toString());
        Double rate = Double.valueOf(request.get("rate").toString());
        Integer months = Integer.valueOf(request.get("months").toString());
        String startDate = (String) request.get("startDate");

        LoanTracker loan = loanTrackerService.addLoan(accountNumber, loanName, bankName, amount, rate, months, startDate);
        return ResponseEntity.ok(loan);
    }

    @GetMapping("/{accountNumber}")
    public ResponseEntity<List<LoanTracker>> getMyLoans(@PathVariable String accountNumber) {
        return ResponseEntity.ok(loanTrackerService.getMyLoans(accountNumber));
    }
}