package com.FaithBank.FaithBank.controller;

import com.FaithBank.FaithBank.Entity.Transaction;
import com.FaithBank.FaithBank.service.BankStatement;
import com.itextpdf.text.DocumentException;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileNotFoundException;
import java.util.List;

@RestController

@AllArgsConstructor
public class TransactionController {

    private final  BankStatement bankStatement;
    @GetMapping("/api/bankStatement")
    public List<Transaction> generateBankStatement(@RequestParam String accountNumber,
                                                   @RequestParam String startDate,
                                                   @RequestParam String endDate) throws DocumentException, FileNotFoundException {
        return bankStatement.generateStatement(accountNumber,startDate, endDate);
    }

}
