package com.FaithBank.FaithBank.controller;

import com.FaithBank.FaithBank.dto.*;
import com.FaithBank.FaithBank.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@Tag(name = "User Account Management Api")
public class UserController {
    @Autowired
    UserService userService;
    @Operation(
            summary = "Create New User Account",
            description = "Account Creation with User Id"

    )
    @ApiResponse(
            responseCode = "201",
            description = "http Status 201 CREATED"
    )

   @PostMapping
    public BankResponse createAccount(@RequestBody UserRequest userRequest)
    {
      return  userService.createAccount(userRequest);

    }
    @Operation(
            summary = "Balance Enquiry on User Account",
            description = "Balance Enquiry"

    )
    @ApiResponse(
            responseCode = "200",
            description = "http Status 200 CREATED"
    )
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
    @PostMapping("transfer")
    public BankResponse TransferRequest(@RequestBody TransferRequest request) {
        return userService.TransferRequest(request);
    }


}
