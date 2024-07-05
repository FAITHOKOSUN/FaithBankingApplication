package com.FaithBank.FaithBank.service;

import com.FaithBank.FaithBank.Entity.User;
import com.FaithBank.FaithBank.dto.AccountInfo;
import com.FaithBank.FaithBank.dto.BankResponse;
import com.FaithBank.FaithBank.dto.UserRequest;
import com.FaithBank.FaithBank.repository.UserRepository;
import com.FaithBank.FaithBank.utils.AccountUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class UserServiceImplementation implements UserService {
    @Autowired
    UserRepository userRepository;

    @Override
    public BankResponse createAccount(UserRequest userRequest) {
        //create acc- service a new user into the database
       //check if a user exist
        if(userRepository.existsByEmail(userRequest.getEmail()))
        {
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
}
