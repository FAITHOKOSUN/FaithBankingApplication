package com.FaithBank.FaithBank.repository;

import com.FaithBank.FaithBank.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

      Boolean existsByEmail(String email);
}
