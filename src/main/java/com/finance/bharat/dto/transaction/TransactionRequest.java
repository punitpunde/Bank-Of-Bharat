package com.finance.bharat.dto.transaction;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionRequest {

    private String transactionId;
    private String accountNumber;
    private String ifscCode;
    private String debitedOrCredited;
    private LocalDateTime localDateTime;
    private double debitOrCreditMoney;
}

