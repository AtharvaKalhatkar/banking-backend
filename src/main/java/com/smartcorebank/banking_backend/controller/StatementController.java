package com.smartcorebank.banking_backend.controller;

import com.itextpdf.text.DocumentException; // ✅ Correct Import
import com.smartcorebank.banking_backend.service.BankStatementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/statements")
public class StatementController {

    @Autowired
    private BankStatementService bankStatementService;

    @GetMapping("/download")
    public ResponseEntity<byte[]> downloadStatement(
            @RequestParam String accountNumber,
            @RequestParam String startDate,
            @RequestParam String endDate) throws DocumentException {

        byte[] pdfBytes = bankStatementService.generateStatementPdf(accountNumber, startDate, endDate);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=statement.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdfBytes);
    }
}