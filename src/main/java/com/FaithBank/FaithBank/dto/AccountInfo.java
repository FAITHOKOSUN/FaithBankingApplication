package com.FaithBank.FaithBank.dto;

import io.swagger.v3.oas.annotations.media.Schema;
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
    @Schema(
            name = "User Account Name"

    )
    private String AccountName;
    @Schema(
            name = "User Account Balance"

    )
    private BigDecimal AccountBalance;
    @Schema(
            name = "User Account Number"

    )
    private String AccountNumber;
}
