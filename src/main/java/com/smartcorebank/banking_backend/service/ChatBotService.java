package com.smartcorebank.banking_backend.service;

import com.smartcorebank.banking_backend.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ChatBotService {

    @Autowired
    private TransactionRepository transactionRepository;

    public String processQuery(String accountNumber, String question) {
        String query = question.toLowerCase();

        // 1. Handle Comparison (Today vs Yesterday)
        if (query.contains("vs") || query.contains("compare")) {
            return handleComparison(accountNumber);
        }

        // 2. Handle Dynamic Day Queries (e.g., "last 5 days", "10 days")
        Pattern dayPattern = Pattern.compile("(\\d+)\\s*days?");
        Matcher matcher = dayPattern.matcher(query);
        if (matcher.find()) {
            int days = Integer.parseInt(matcher.group(1));
            return getTimedSpending(accountNumber, days, "the last " + days + " days");
        }

        // 3. Handle Standard Time Labels
        if (query.contains("today")) {
            return getTimedSpending(accountNumber, 0, "today");
        } else if (query.contains("yesterday")) {
            return getYesterdaySpending(accountNumber);
        } else if (query.contains("last week") || query.contains("7 days")) {
            return getTimedSpending(accountNumber, 7, "the last week");
        } else if (query.contains("this week")) {
            // "This week" usually means from last Monday, but we'll use 7 days for consistency
            return getTimedSpending(accountNumber, 7, "this week");
        } else if (query.contains("month") || query.contains("30 days")) {
            return getTimedSpending(accountNumber, 30, "this month");
        }

        return "I didn't quite get that. You can ask 'How much did I spend in the last 5 days?' or 'Compare today vs yesterday'.";
    }

    private String getTimedSpending(String acc, int days, String label) {
        LocalDateTime start = LocalDateTime.now().minusDays(days).truncatedTo(ChronoUnit.DAYS);
        LocalDateTime end = LocalDateTime.now();
        
        // If it's just "today", we only look from midnight today
        if (days == 0) {
            start = LocalDateTime.now().truncatedTo(ChronoUnit.DAYS);
        }

        Double spent = transactionRepository.calculateSpendingInRange(acc, start, end);
        return "You spent ₹" + String.format("%.2f", (spent == null ? 0.0 : spent)) + " " + label + ".";
    }

    private String getYesterdaySpending(String acc) {
        LocalDateTime start = LocalDateTime.now().minusDays(1).truncatedTo(ChronoUnit.DAYS);
        LocalDateTime end = LocalDateTime.now().truncatedTo(ChronoUnit.DAYS);
        Double spent = transactionRepository.calculateSpendingInRange(acc, start, end);
        return "You spent ₹" + String.format("%.2f", (spent == null ? 0.0 : spent)) + " yesterday.";
    }

    private String handleComparison(String accountNumber) {
        LocalDateTime startToday = LocalDateTime.now().truncatedTo(ChronoUnit.DAYS);
        Double spentToday = getSpending(accountNumber, startToday, LocalDateTime.now());

        LocalDateTime startYesterday = startToday.minus(1, ChronoUnit.DAYS);
        Double spentYesterday = getSpending(accountNumber, startYesterday, startToday);

        String result = "Today: ₹" + String.format("%.2f", spentToday) + " | Yesterday: ₹" + String.format("%.2f", spentYesterday) + ". ";

        if (spentToday > spentYesterday) {
            result += "You spent ₹" + String.format("%.2f", (spentToday - spentYesterday)) + " more today.";
        } else if (spentYesterday > spentToday) {
            result += "You spent ₹" + String.format("%.2f", (spentYesterday - spentToday)) + " less today. Well done!";
        } else {
            result += "Your spending is consistent with yesterday.";
        }
        return result;
    }

    private Double getSpending(String account, LocalDateTime start, LocalDateTime end) {
        Double val = transactionRepository.calculateSpendingInRange(account, start, end);
        return (val == null) ? 0.0 : val;
    }
}