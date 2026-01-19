package com.smartcorebank.banking_backend.service;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;
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
        // Formats for logic and display
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        DateTimeFormatter displayFormatter = DateTimeFormatter.ofPattern("dd MMM yyyy, HH:mm");
        
        LocalDateTime start = LocalDateTime.parse(startDateStr + " 00:00", inputFormatter);
        LocalDateTime end = LocalDateTime.parse(endDateStr + " 23:59", inputFormatter);

        // Uses catch-all query for both old and new column formats
        List<Transaction> transactions = transactionRepository.findForStatement(accountNumber, start, end);

        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PdfWriter.getInstance(document, out);

        document.open();

        // --- 1. Bank Branding & Header ---
        Font bankNameFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 22, BaseColor.BLACK);
        Paragraph bankName = new Paragraph("SMART CORE BANK", bankNameFont);
        bankName.setAlignment(Element.ALIGN_LEFT);
        document.add(bankName);

        Font branchFont = FontFactory.getFont(FontFactory.HELVETICA, 10, BaseColor.GRAY);
        document.add(new Paragraph("Corporate Office: Pune Tech Park, Maharashtra, India", branchFont));
        document.add(new Paragraph("Email: support@smartcorebank.com | Web: www.smartcorebank.com", branchFont));
        
        document.add(new Paragraph(" "));
        LineSeparator ls = new LineSeparator(); // Fixed: Added required import
        ls.setLineColor(new BaseColor(200, 200, 200));
        document.add(new Chunk(ls));
        document.add(new Paragraph(" "));

        // --- 2. Statement Title & Info ---
        Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14);
        Paragraph title = new Paragraph("ACCOUNT STATEMENT", titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);
        document.add(new Paragraph(" "));

        // Account Details Table
        PdfPTable details = new PdfPTable(2);
        details.setWidthPercentage(100);
        // Fixed: Removed setBorderWidth which does not exist for PdfPTable
        
        details.addCell(getNoBorderCell("Account Number: " + accountNumber));
        details.addCell(getNoBorderCell("Statement Date: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))));
        details.addCell(getNoBorderCell("Period: " + startDateStr + " to " + endDateStr));
        details.addCell(getNoBorderCell("Currency: INR (₹)"));
        
        document.add(details);
        document.add(new Paragraph(" "));

        // --- 3. Transaction Table ---
        PdfPTable table = new PdfPTable(new float[]{30, 45, 25}); 
        table.setWidthPercentage(100);
        
        addTableHeader(table);

        for (Transaction t : transactions) {
            table.addCell(new Phrase(t.getTimestamp().format(displayFormatter), FontFactory.getFont(FontFactory.HELVETICA, 10)));
            table.addCell(new Phrase(t.getType(), FontFactory.getFont(FontFactory.HELVETICA, 10)));
            
            // Professional currency formatting
            String amountText = "₹" + String.format("%.2f", t.getAmount());
            PdfPCell amountCell = new PdfPCell(new Phrase(amountText, FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10)));
            amountCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            amountCell.setPaddingRight(5);
            table.addCell(amountCell);
        }

        document.add(table);

        // --- 4. Professional Footer ---
        document.add(new Paragraph(" "));
        document.add(new Paragraph(" "));
        Font footerFont = FontFactory.getFont(FontFactory.HELVETICA_OBLIQUE, 9, BaseColor.GRAY);
        Paragraph footer = new Paragraph("This is a computer-generated document. For any discrepancies, please contact your nearest branch within 15 days.", footerFont);
        footer.setAlignment(Element.ALIGN_CENTER);
        document.add(footer);

        document.close();
        return out.toByteArray();
    }

    private void addTableHeader(PdfPTable table) {
        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 11, BaseColor.WHITE);
        String[] headers = {"DATE & TIME", "TRANSACTION TYPE", "AMOUNT"};
        
        for (String header : headers) {
            PdfPCell cell = new PdfPCell(new Phrase(header, font));
            cell.setBackgroundColor(new BaseColor(44, 62, 80)); 
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setPadding(8);
            table.addCell(cell);
        }
    }

    private PdfPCell getNoBorderCell(String text) {
        PdfPCell cell = new PdfPCell(new Phrase(text, FontFactory.getFont(FontFactory.HELVETICA, 11)));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setPaddingBottom(5);
        return cell;
    }
}