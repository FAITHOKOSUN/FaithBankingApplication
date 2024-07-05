package com.FaithBank.FaithBank.service;

import com.FaithBank.FaithBank.dto.BankResponse;
import com.FaithBank.FaithBank.dto.UserRequest;

public interface UserService {
    BankResponse createAccount (UserRequest userRequest);
}
