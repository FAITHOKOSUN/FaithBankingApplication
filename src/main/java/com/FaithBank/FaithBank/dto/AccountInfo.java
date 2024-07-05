package com.FaithBank.FaithBank.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class AccountInfo {
    private String AccountName;
    private BigDecimal AccountBalance;
    private String AccountNumber;
}
