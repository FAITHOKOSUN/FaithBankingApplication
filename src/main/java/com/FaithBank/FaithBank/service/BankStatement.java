package com.FaithBank.FaithBank.service;

import com.FaithBank.FaithBank.Entity.Transaction;
import com.FaithBank.FaithBank.Entity.User;
import com.FaithBank.FaithBank.dto.EmailDetails;
import com.FaithBank.FaithBank.repository.UserRepository;
import com.FaithBank.FaithBank.repository.transactionRepo;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
@AllArgsConstructor
@Slf4j
public class BankStatement {
    private transactionRepo transactionRepo;
    private UserRepository userRepository;
    private EmailService emailService;
    private static final String FILE = "C:\\Users\\Faith\\Documents\\MyStatement.pdf";

    /**
     * Get the list of transactions within a date range on account number
     * Generate PDF
     * Send file via mail
     */
    public List<Transaction> generateStatement(String accountNumber, String startDate, String endDate) throws FileNotFoundException, DocumentException {
        LocalDate start = LocalDate.parse(startDate, DateTimeFormatter.ISO_DATE);
        LocalDate end = LocalDate.parse(endDate, DateTimeFormatter.ISO_DATE);

        List<Transaction> transactionList = transactionRepo.findAll().stream()
                .filter(transaction -> transaction.getAccountNumber() != null && transaction.getAccountNumber().equals(accountNumber))
                .filter(transaction -> !transaction.getCreatedAt().isBefore(start))
                .filter(transaction -> !transaction.getCreatedAt().isAfter(end))
                .toList();

        User user = userRepository.findByAccountNumber(accountNumber);
        String customerName = user.getFirstName() + " " + user.getLastName() + " " + user.getOtherName();
        Rectangle statementSize = new Rectangle(PageSize.A4);
        Document document = new Document(statementSize);
        log.info("Setting document size");
        OutputStream outputStream = new FileOutputStream(FILE);
        PdfWriter.getInstance(document, outputStream);
        document.open();

        // Bank Information Header Table
        PdfPTable headerTable = new PdfPTable(3);
        headerTable.setWidthPercentage(100);

        // Bank Name (Center)
        PdfPCell bankNameCell = new PdfPCell(new Phrase("FAITH BANK PLC"));
        bankNameCell.setBackgroundColor(BaseColor.YELLOW);
        bankNameCell.setPadding(10f);
        bankNameCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        bankNameCell.setColspan(3); // Span across the entire header table
        headerTable.addCell(bankNameCell);

        // Period Text Under Bank Name
        PdfPCell periodCell = new PdfPCell(new Phrase("Period: " + startDate + " to " + endDate));
        periodCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        periodCell.setBorder(Rectangle.NO_BORDER);
        periodCell.setColspan(3); // Span across the entire header table
        headerTable.addCell(periodCell);

        // Address (Left)
        PdfPCell bankAddressCell = new PdfPCell(new Phrase("11 Adetola Adebiyi Street Victoria Island Lagos, Nigeria."));
        bankAddressCell.setBorder(Rectangle.NO_BORDER);
        headerTable.addCell(bankAddressCell);

        // Empty cell for spacing
        PdfPCell emptyCell = new PdfPCell(new Phrase(""));
        emptyCell.setBorder(Rectangle.NO_BORDER);
        headerTable.addCell(emptyCell);

        // Statement of Account and Customer Info (Right)
        PdfPTable statementInfo = new PdfPTable(1);
        statementInfo.setWidthPercentage(100);

        PdfPCell statementCell = new PdfPCell(new Phrase("Statement of Account"));
        statementCell.setBorder(Rectangle.NO_BORDER);
        statementCell.setPaddingBottom(5f); // Add spacing between items
        statementInfo.addCell(statementCell);

        PdfPCell customerNameCell = new PdfPCell(new Phrase("Customer Name: " + customerName));
        customerNameCell.setBorder(Rectangle.NO_BORDER);
        customerNameCell.setPaddingBottom(5f); // Add spacing between items
        statementInfo.addCell(customerNameCell);

        PdfPCell customerAccountCell = new PdfPCell(new Phrase("Account Number: " + accountNumber));
        customerAccountCell.setBorder(Rectangle.NO_BORDER);
        customerAccountCell.setPaddingBottom(5f); // Add spacing between items
        statementInfo.addCell(customerAccountCell);

        PdfPCell customerAddressCell = new PdfPCell(new Phrase("Customer Address: " + user.getAddress()));
        customerAddressCell.setBorder(Rectangle.NO_BORDER);
        customerAddressCell.setPaddingBottom(5f); // Add spacing between items
        statementInfo.addCell(customerAddressCell);

        PdfPCell rightCell = new PdfPCell(statementInfo);
        rightCell.setBorder(Rectangle.NO_BORDER);
        headerTable.addCell(rightCell);

        // Dates
        PdfPCell startDateCell = new PdfPCell(new Phrase("Start Date: " + startDate));
        startDateCell.setBorder(Rectangle.NO_BORDER);
        headerTable.addCell(startDateCell);

        PdfPCell endDateCell = new PdfPCell(new Phrase("End Date: " + endDate));
        endDateCell.setBorder(Rectangle.NO_BORDER);
        headerTable.addCell(endDateCell);

        // Adding header table to document
        document.add(headerTable);

        // Transaction Table
        PdfPTable transactionTable = new PdfPTable(4);
        transactionTable.setWidthPercentage(100);

        PdfPCell dateCell = new PdfPCell(new Phrase("DATE"));
        dateCell.setBackgroundColor(BaseColor.YELLOW);
        transactionTable.addCell(dateCell);

        PdfPCell transactionTypeCell = new PdfPCell(new Phrase("Transaction Type"));
        transactionTypeCell.setBackgroundColor(BaseColor.YELLOW);
        transactionTable.addCell(transactionTypeCell);

        PdfPCell transactionAmountCell = new PdfPCell(new Phrase("Transaction Amount"));
        transactionAmountCell.setBackgroundColor(BaseColor.YELLOW);
        transactionTable.addCell(transactionAmountCell);

        PdfPCell statusCell = new PdfPCell(new Phrase("STATUS"));
        statusCell.setBackgroundColor(BaseColor.YELLOW);
        transactionTable.addCell(statusCell);

        transactionList.forEach(transaction -> {
            transactionTable.addCell(new Phrase(transaction.getCreatedAt().toString()));
            transactionTable.addCell(new Phrase(transaction.getTransactionType()));
            transactionTable.addCell(new Phrase(transaction.getAmount().toString())); // Ensure amount is added
            transactionTable.addCell(new Phrase(transaction.getStatus()));
        });

        document.add(transactionTable);
        document.close();

        EmailDetails emailDetails = EmailDetails.builder()
                .recipient(user.getEmail())
                .subject("AUGUST 2024 STATEMENT OF ACCOUNT")
                .messageBody("Dear "+ user.getFirstName() + " " + user.getLastName() + " " + user.getOtherName() +
                       "\nYour statement of account for August 2024 is now available (see attached) ")
                .attachment(FILE)
                .build();

            emailService.sendEmailAlertWithAttachment(emailDetails);

        return transactionList;
    }
}
