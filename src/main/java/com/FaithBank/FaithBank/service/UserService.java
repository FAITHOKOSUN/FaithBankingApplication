package com.FaithBank.FaithBank.service;

import com.FaithBank.FaithBank.dto.*;

public interface UserService {
    BankResponse createAccount (UserRequest userRequest);

    BankResponse balEnquiry(EnquiryRequest request);
    String nameEnquiry(EnquiryRequest request);

    public BankResponse creditAccount(CreditDebitRequest request);
    public BankResponse debitAccount(CreditDebitRequest request);

    public BankResponse TransferRequest(TransferRequest request);
    BankResponse login(LoginDto loginDto);
}

