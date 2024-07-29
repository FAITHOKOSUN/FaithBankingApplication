package com.FaithBank.FaithBank.repository;

import com.FaithBank.FaithBank.Entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface transactionRepo extends JpaRepository<Transaction, String> {

}
