package com.FaithBank.FaithBank.service;

import com.FaithBank.FaithBank.Entity.Transaction;
import com.FaithBank.FaithBank.dto.Transactiondto;

public interface transactionService {

    void saveTransaction(Transactiondto transaction);
}
