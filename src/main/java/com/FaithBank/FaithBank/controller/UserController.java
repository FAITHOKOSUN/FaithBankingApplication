package com.FaithBank.FaithBank.controller;

import com.FaithBank.FaithBank.dto.BankResponse;
import com.FaithBank.FaithBank.dto.CreditDebitRequest;
import com.FaithBank.FaithBank.dto.EnquiryRequest;
import com.FaithBank.FaithBank.dto.UserRequest;
import com.FaithBank.FaithBank.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    UserService userService;

   @PostMapping
    public BankResponse createAccount(@RequestBody UserRequest userRequest)
    {
      return  userService.createAccount(userRequest);

    }
    @GetMapping("balanceEnquiry")
    public  BankResponse balEnquiry(@RequestBody EnquiryRequest request)
    {
        return userService.balEnquiry(request);
    }
    @GetMapping("nameEnq")
    public String nameEnq(@RequestBody EnquiryRequest request)
    {
        return userService.nameEnquiry(request);
    }

    @PostMapping("credit")
    public BankResponse creditAccount(@RequestBody CreditDebitRequest request)
    {
        return  userService.creditAccount(request);

    }

    @PostMapping("debit")
    public BankResponse debitAccount(@RequestBody CreditDebitRequest request)
    {
        return  userService.debitAccount(request);

    }


}
