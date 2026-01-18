package com.smartcorebank.banking_backend.controller;

import com.smartcorebank.banking_backend.entity.SavingsGoal;
import com.smartcorebank.banking_backend.service.SavingsGoalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/savings-goals")
public class SavingsGoalController {

    @Autowired
    private SavingsGoalService savingsGoalService;

    // 1. Create a Goal (e.g., "Goa Trip", Target: 10000)
    @PostMapping("/create")
    public ResponseEntity<SavingsGoal> createGoal(@RequestBody Map<String, Object> request) {
        String accountNumber = (String) request.get("accountNumber");
        String goalName = (String) request.get("goalName");
        Double targetAmount = Double.valueOf(request.get("targetAmount").toString());

        SavingsGoal goal = savingsGoalService.createGoal(accountNumber, goalName, targetAmount);
        return ResponseEntity.ok(goal);
    }

    // 2. Add Funds to Goal (Locks money from main account)
    @PostMapping("/add-funds")
    public ResponseEntity<?> addFunds(@RequestBody Map<String, Object> request) {
        Long goalId = Long.valueOf(request.get("goalId").toString());
        Double amount = Double.valueOf(request.get("amount").toString());

        try {
            SavingsGoal updatedGoal = savingsGoalService.addFunds(goalId, amount);
            return ResponseEntity.ok(updatedGoal);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    // 3. View My Goals
    @GetMapping("/{accountNumber}")
    public ResponseEntity<List<SavingsGoal>> getMyGoals(@PathVariable String accountNumber) {
        return ResponseEntity.ok(savingsGoalService.getMyGoals(accountNumber));
    }
}