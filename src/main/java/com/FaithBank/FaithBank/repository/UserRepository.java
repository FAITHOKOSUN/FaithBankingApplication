package com.FaithBank.FaithBank.repository;

import com.FaithBank.FaithBank.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

      Boolean existsByEmail(String email);


      Optional<User> findByEmail(String email);

      Boolean existsByAccountNumber(String accountNumber);

      User findByAccountNumber(String accountNumber);


}
