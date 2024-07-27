package com.FaithBank.FaithBank.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransferRequest {
    private String sourceAccount;
    private String destinationAccount;
    private BigDecimal amount;
}
