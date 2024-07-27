package com.FaithBank.FaithBank.service;

import com.FaithBank.FaithBank.Entity.User;
import com.FaithBank.FaithBank.dto.*;
import com.FaithBank.FaithBank.repository.UserRepository;
import com.FaithBank.FaithBank.utils.AccountUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class UserServiceImplementation implements UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    EmailService emailService;

    @Override
    public BankResponse createAccount(UserRequest userRequest) {
        //create acc- service a new user into the database
        //check if a user exist
        if (userRepository.existsByEmail(userRequest.getEmail())) {
            return BankResponse.builder()
                    .responseCode(AccountUtils.ACCOUNT_EXISTS_CODE)
                    .responseMessage(AccountUtils.ACCOUNT_EXISTS_MESSAGE)
                    .accountInfo(null)
                    .build();


        }
        User newuser = User.builder()
                .firstName(userRequest.getFirstName())
                .lastName(userRequest.getLastName())
                .otherName(userRequest.getOtherName())
                .gender(userRequest.getGender())
                .address(userRequest.getAddress())
                .stateOfOrigin(userRequest.getStateOfOrigin())
                .accountNumber(AccountUtils.generateAccNumb())
                .accountBalance(BigDecimal.ZERO)
                .email(userRequest.getEmail())
                .phoneNumber(userRequest.getPhoneNumber())
                .alternativePhoneNumber(userRequest.getAlternativePhoneNumber())
                .status("Active")

                .build();
        User saveuser = userRepository.save(newuser);
        //send email alert

        EmailDetails emailDetails = EmailDetails.builder()
                .recipient(saveuser.getEmail())
                .subject("WELCOME TO FAITH BANK-NEW ACCOUNT DETAILS")
                .messageBody("Dear Sarah, \nCongratulations, your account has been successfully created!\nYour Account details: \n " + "Account Name:" + saveuser.getFirstName() + " " + saveuser.getOtherName() + " " + saveuser.getLastName() + "\nAccount Number:" + saveuser.getAccountNumber())

                .build();
        emailService.sendEmailAlert(emailDetails);
        return BankResponse.builder()
                .responseCode(AccountUtils.ACCOUNT_CREATION_SUCCESS)
                .responseMessage(AccountUtils.ACCOUNT_CREATION_MESSAGE)
                .accountInfo(AccountInfo.builder()
                        .AccountName(saveuser.getFirstName() + " " + saveuser.getOtherName() + " " + saveuser.getLastName()

                        )
                        .AccountBalance(saveuser.getAccountBalance())
                        .AccountNumber(saveuser.getAccountNumber())

                        .build())
                .build();


    }

    //Name enquiry,balance,credit, debit, and transfer
    @Override
    public BankResponse balEnquiry(EnquiryRequest request) {
        //if the acct numb exist in db
        boolean acctExist = userRepository.existsByAccountNumber(request.getAccountNumber());
        if (!acctExist) {
            return BankResponse.builder()
                    .responseMessage(AccountUtils.ACCOUNT_NOT_EXIST_MESSAGE)
                    .responseCode(AccountUtils.ACCOUNT_NOT_EXIST_CODE)
                    .accountInfo(null)
                    .build();
        }
        User founduser = userRepository.findByAccountNumber(request.getAccountNumber());
        return BankResponse.builder()
                .responseCode(AccountUtils.ACCOUNT_FOUND_CODE)
                .responseMessage(AccountUtils.ACCOUNT_FOUND_MESSAGE)
                .accountInfo(AccountInfo.builder()
                        .AccountBalance(founduser.getAccountBalance())
                        .AccountNumber(request.getAccountNumber())
                        .AccountName(founduser.getFirstName() + " " + founduser.getOtherName() + " " + founduser.getLastName())

                        .build())
                .build();
    }

    @Override
    public String nameEnquiry(EnquiryRequest request) {
        Boolean nameExist = userRepository.existsByAccountNumber(request.getAccountNumber());
        if (!nameExist) {
            return AccountUtils.ACCOUNT_NOT_EXIST_MESSAGE;

        }
        User founduser = userRepository.findByAccountNumber(request.getAccountNumber());
        return founduser.getFirstName() + " " + founduser.getOtherName() + " " + founduser.getLastName();

    }

    @Override
    public BankResponse creditAccount(CreditDebitRequest request) {
        //checking if the account exist
        boolean acctExist = userRepository.existsByAccountNumber(request.getAccountNumber());
        if (!acctExist) {
            return BankResponse.builder()
                    .responseMessage(AccountUtils.ACCOUNT_NOT_EXIST_MESSAGE)
                    .responseCode(AccountUtils.ACCOUNT_NOT_EXIST_CODE)
                    .accountInfo(null)
                    .build();
        }
        User userCredit = userRepository.findByAccountNumber(request.getAccountNumber());
        userCredit.setAccountBalance(userCredit.getAccountBalance().add(request.getAmount()));
        userRepository.save(userCredit);
        return BankResponse.builder()
                .responseMessage(AccountUtils.ACCOUNT_CREDIT_MESSAGE)
                .responseCode(AccountUtils.ACCOUNT_CREDIT_CODE)
                .accountInfo(AccountInfo.builder()
                        .AccountName(userCredit.getFirstName() + " " + userCredit.getOtherName() + " " + userCredit.getLastName())
                        .AccountBalance(userCredit.getAccountBalance())
                        .AccountNumber(request.getAccountNumber())
                        .build())
                .build();


    }

    @Override
    public BankResponse debitAccount(CreditDebitRequest request) {
        //checking if the account exist
        boolean acctExist = userRepository.existsByAccountNumber(request.getAccountNumber());
        if (!acctExist) {
            return BankResponse.builder()
                    .responseMessage(AccountUtils.ACCOUNT_NOT_EXIST_MESSAGE)
                    .responseCode(AccountUtils.ACCOUNT_NOT_EXIST_CODE)
                    .accountInfo(null)
                    .build();
        }
        // Retrieve user account information
        User userDebit = userRepository.findByAccountNumber(request.getAccountNumber());
        // Check if the account balance is less than the amount to be withdrawn
        if (userDebit.getAccountBalance().compareTo(request.getAmount()) < 0)

            return BankResponse.builder()
                    .responseMessage(AccountUtils.INSUFFICIENT_BALANCE)
                    .responseCode(AccountUtils.ACCOUNT_DEBIT_CODE)
                    .accountInfo(AccountInfo.builder()
                            .AccountName(userDebit.getFirstName() + " " + userDebit.getOtherName() + " " + userDebit.getLastName())
                            .AccountBalance(userDebit.getAccountBalance())
                            .AccountNumber(request.getAccountNumber())
                            .build())
                    .build();
        // Deduct the amount from the account balance
        userDebit.setAccountBalance(userDebit.getAccountBalance().subtract(request.getAmount()));

        // Save the updated account information
        userRepository.save(userDebit);

        // Return the response for successful debit
        return BankResponse.builder()
                .responseMessage(AccountUtils.ACCOUNT_DEBIT_MESSAGE)
                .responseCode(AccountUtils.ACCOUNT_DEBIT_CODE)
                .accountInfo(AccountInfo.builder()
                        .AccountName(userDebit.getFirstName() + " " + userDebit.getOtherName() + " " + userDebit.getLastName())
                        .AccountBalance(userDebit.getAccountBalance())
                        .AccountNumber(request.getAccountNumber())
                        .build())
                .build();
    }

    @Override
    public BankResponse TransferRequest(TransferRequest request) {
        //Acc to debit(if it exist)
        //check if the source acc(to debit) is not more than the current bal
        //debit acc
        //get acc to credit
        //credit acc


        // Check if the destination account exists
        boolean destinationacctExist = userRepository.existsByAccountNumber(request.getDestinationAccount());
        if (!destinationacctExist) {
            return BankResponse.builder()
                    .responseMessage(AccountUtils.ACCOUNT_NOT_EXIST_MESSAGE)
                    .responseCode(AccountUtils.ACCOUNT_NOT_EXIST_CODE)
                    .accountInfo(null)
                    .build();
        }

        // Check if the source account exists and has sufficient balance
        User sourceacctuser = userRepository.findByAccountNumber(request.getSourceAccount());
        if (sourceacctuser == null) {
            return BankResponse.builder()
                    .responseMessage(AccountUtils.ACCOUNT_NOT_EXIST_MESSAGE)
                    .responseCode(AccountUtils.ACCOUNT_NOT_EXIST_CODE)
                    .accountInfo(null)
                    .build();
        }
        if (request.getAmount().compareTo(sourceacctuser.getAccountBalance()) > 0) {
            return BankResponse.builder()
                    .responseMessage(AccountUtils.INSUFFICIENT_BALANCE)
                    .responseCode(AccountUtils.ACCOUNT_DEBIT_CODE)
                    .accountInfo(null)
                    .build();
        }

        // Debit the source account
        sourceacctuser.setAccountBalance(sourceacctuser.getAccountBalance().subtract(request.getAmount()));
        userRepository.save(sourceacctuser);

        // Send debit alert
        EmailDetails debitAlert = EmailDetails.builder()
                .subject("Debit Alert!")
                .recipient(sourceacctuser.getEmail())
                .messageBody(request.getAmount() + "has been deducted from your account.Current balance is " + sourceacctuser.getAccountBalance())
                .build();
        emailService.sendEmailAlert(debitAlert);

        // Credit the destination account

        User destinationAcct = userRepository.findByAccountNumber(request.getDestinationAccount());
        destinationAcct.setAccountBalance(destinationAcct.getAccountBalance().add(request.getAmount()));
        userRepository.save(destinationAcct);

        // Send credit alert
        EmailDetails creditAlert = EmailDetails.builder()
                .subject("Credit Alert!")
                .recipient(destinationAcct.getEmail())
                .messageBody(request.getAmount() + " has been credited to your account from " + sourceacctuser.getFirstName() + " " + sourceacctuser.getLastName() + ". Current balance: " + destinationAcct.getAccountBalance())
                .build();
        emailService.sendEmailAlert(creditAlert);

        return BankResponse.builder()
                .responseCode("009")
                .responseMessage("Transfer successful")
                .accountInfo(null)
                .build();

    }
}