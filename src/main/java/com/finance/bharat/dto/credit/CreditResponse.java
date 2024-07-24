package com.finance.bharat.dto.credit;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreditResponse {

    private String creditId;
    private double currentBalance;
    private String accountHolderName;
    private String accountNumber;
    private String ifscCode;
    private String bankName;
    private String statusMoney;
    private LocalDateTime localDateTime;


}
