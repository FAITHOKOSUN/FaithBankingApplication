package com.FaithBank.FaithBank.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor


public class UserRequest {

    private  String firstName;
    private  String lastName;
    private  String otherName;
    private  String gender;
    private  String address;
    private  String stateOfOrigin;
    private  String email;
    private  String password;
    private  String phoneNumber;
    private  String alternativePhoneNumber;


}
