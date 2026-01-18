package com.smartcorebank.banking_backend.service;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.smartcorebank.banking_backend.entity.Transaction;
import com.smartcorebank.banking_backend.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class BankStatementService {

    @Autowired
    private TransactionRepository transactionRepository;

    public byte[] generateStatementPdf(String accountNumber, String startDateStr, String endDateStr) throws DocumentException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime start = LocalDateTime.parse(startDateStr + " 00:00", formatter);
        LocalDateTime end = LocalDateTime.parse(endDateStr + " 23:59", formatter);

        List<Transaction> transactions = transactionRepository.findForStatement(accountNumber, start, end);

        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PdfWriter.getInstance(document, out);

        document.open();
        document.add(new Paragraph("Bank Statement"));
        document.add(new Paragraph("Account: " + accountNumber));
        document.add(new Paragraph("Period: " + startDateStr + " to " + endDateStr));
        document.add(new Paragraph(" "));

        PdfPTable table = new PdfPTable(3);
        table.addCell("Date");
        table.addCell("Type");
        table.addCell("Amount");

        for (Transaction t : transactions) {
            table.addCell(t.getTimestamp().toString());
            table.addCell(t.getType());
            table.addCell(t.getAmount().toString());
        }

        document.add(table);
        document.close();

        return out.toByteArray();
    }
}