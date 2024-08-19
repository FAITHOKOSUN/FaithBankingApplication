package com.FaithBank.FaithBank.service;

import com.FaithBank.FaithBank.Entity.Transaction;
import com.FaithBank.FaithBank.dto.Transactiondto;
import com.FaithBank.FaithBank.repository.transactionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class transactionServiceImple implements transactionService{
    @Autowired
    transactionRepo transactionRepo;
    @Override
    public void saveTransaction(Transactiondto transactiondto) {

    Transaction transaction = Transaction.builder()
            .transactionType(transactiondto.getTransactionType())
            .transactionId(transactiondto.getAccountNumber())
            .accountNumber(transactiondto.getAccountNumber())
            .amount(transactiondto.getAmount())
            .status(transactiondto.getStatus() != null ? transactiondto.getStatus() : "SUCCESS")

            .build();
    transactionRepo.save(transaction);
        System.out.println("Transaction generated successfully");
    }
}

