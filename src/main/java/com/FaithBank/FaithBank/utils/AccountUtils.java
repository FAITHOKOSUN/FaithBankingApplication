package com.FaithBank.FaithBank.utils;

import java.time.Year;

public class AccountUtils {

    public static final String ACCOUNT_EXISTS_CODE = "001";
    public static final String ACCOUNT_EXISTS_MESSAGE = "Your account already exists dear friend";
    public static final String ACCOUNT_CREATION_SUCCESS = "002";
    public static final String ACCOUNT_CREATION_MESSAGE= "Account successfully created";
    public static final String ACCOUNT_FOUND_MESSAGE = "Hello, here is your balance enquiry";
    public static final String ACCOUNT_FOUND_CODE = "004";
    public static final String ACCOUNT_CREDIT_MESSAGE = "Hello,Your account has been successfully credited";
    public static final String ACCOUNT_CREDIT_CODE = "005";
    public static final String ACCOUNT_DEBIT_MESSAGE = "Hello,Your account has been successfully debited";
    public static final String ACCOUNT_DEBIT_CODE = "006";
    public static  final String ACCOUNT_NOT_EXIST_CODE = "008";
    public static final String ACCOUNT_NOT_EXIST_MESSAGE = "Account number does not exist ";

    public static final String INSUFFICIENT_BALANCE= "Insufficient funds";
    public static final String ACCOUNT_CREATION_FAILED = "ACCOUNT_CREATION_FAILED";
public static String generateAccNumb()
{
    //    2024 + random six digits to get 10 digits acc number
    Year currentYear = Year.now();
    int min = 100000;
    int max = 999000;
    //generate a random number btw min and max
    int randomNumb = (int)Math.floor(Math.random() * (max - min + 1) + min);

    //convert current year and random numb to string and concatenate
    String year = String.valueOf(currentYear);
    String randomNumber = String.valueOf(randomNumb);
    return year + randomNumber;

//    StringBuilder accountNumber = new StringBuilder();
//
//
//    return accountNumber.append(year).append(randomNumber).tostring();


}

}

