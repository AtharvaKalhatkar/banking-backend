package com.smartcorebank.banking_backend.service;

import com.smartcorebank.banking_backend.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Service
public class ChatBotService {

    @Autowired
    private TransactionRepository transactionRepository;

    public String processQuery(String accountNumber, String question) {
        String lowerCaseQuestion = question.toLowerCase();

        // 1. COMPARISON: "Today vs Yesterday"
        if (lowerCaseQuestion.contains("vs") || lowerCaseQuestion.contains("compare")) {
            return handleComparison(accountNumber);
        }

        // 2. SIMPLE TIME QUERIES
        LocalDateTime start;
        LocalDateTime end = LocalDateTime.now();
        String label;

        if (lowerCaseQuestion.contains("today")) {
            start = LocalDateTime.now().truncatedTo(ChronoUnit.DAYS); // Midnight Today
            label = "today";
        } else if (lowerCaseQuestion.contains("yesterday")) {
            start = LocalDateTime.now().minus(1, ChronoUnit.DAYS).truncatedTo(ChronoUnit.DAYS); // Midnight Yesterday
            end = LocalDateTime.now().truncatedTo(ChronoUnit.DAYS); // Midnight Today (Stop counting)
            label = "yesterday";
        } else if (lowerCaseQuestion.contains("week")) {
            start = LocalDateTime.now().minus(7, ChronoUnit.DAYS);
            label = "this week";
        } else if (lowerCaseQuestion.contains("month")) {
            start = LocalDateTime.now().minus(30, ChronoUnit.DAYS);
            label = "this month";
        } else {
            return "I didn't understand. Try asking about 'today', 'yesterday', or 'vs'.";
        }

        Double spent = getSpending(accountNumber, start, end);
        return "You spent ₹" + spent + " " + label + ".";
    }

    // Helper logic for Comparison
    private String handleComparison(String accountNumber) {
        // Calculate Today
        LocalDateTime startToday = LocalDateTime.now().truncatedTo(ChronoUnit.DAYS);
        Double spentToday = getSpending(accountNumber, startToday, LocalDateTime.now());

        // Calculate Yesterday
        LocalDateTime startYesterday = startToday.minus(1, ChronoUnit.DAYS);
        Double spentYesterday = getSpending(accountNumber, startYesterday, startToday);

        String result = "Today: ₹" + spentToday + " | Yesterday: ₹" + spentYesterday + ". ";

        if (spentToday > spentYesterday) {
            result += "You spent ₹" + (spentToday - spentYesterday) + " more today.";
        } else if (spentYesterday > spentToday) {
            result += "You spent ₹" + (spentYesterday - spentToday) + " less today. Good job!";
        } else {
            result += "Your spending is exactly the same.";
        }
        
        return result;
    }

    // Database Helper
    private Double getSpending(String account, LocalDateTime start, LocalDateTime end) {
        Double val = transactionRepository.calculateSpendingInRange(account, start, end);
        return (val == null) ? 0.0 : val;
    }
}