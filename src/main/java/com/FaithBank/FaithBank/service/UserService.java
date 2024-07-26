package com.FaithBank.FaithBank.service;

import com.FaithBank.FaithBank.dto.BankResponse;
import com.FaithBank.FaithBank.dto.CreditDebitRequest;
import com.FaithBank.FaithBank.dto.EnquiryRequest;
import com.FaithBank.FaithBank.dto.UserRequest;

public interface UserService {
    BankResponse createAccount (UserRequest userRequest);

    BankResponse balEnquiry(EnquiryRequest request);
    String nameEnquiry(EnquiryRequest request);

    public BankResponse creditAccount(CreditDebitRequest request);
    public BankResponse debitAccount(CreditDebitRequest request);
}
