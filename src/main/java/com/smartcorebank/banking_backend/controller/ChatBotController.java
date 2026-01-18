package com.smartcorebank.banking_backend.controller;

import com.smartcorebank.banking_backend.service.ChatBotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/chat")
public class ChatBotController {

    @Autowired
    private ChatBotService chatBotService;

    // Endpoint: POST /api/chat/ask
    @PostMapping("/ask")
    public ResponseEntity<Map<String, String>> askBot(@RequestBody Map<String, String> request) {
        String accountNumber = request.get("accountNumber");
        String question = request.get("question");

        String answer = chatBotService.processQuery(accountNumber, question);

        return ResponseEntity.ok(Map.of("answer", answer));
    }
}