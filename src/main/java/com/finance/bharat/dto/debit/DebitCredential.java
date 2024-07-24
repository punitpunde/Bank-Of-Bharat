package com.finance.bharat.dto.debit;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DebitCredential {

    private String accountNumber;
    private String password;
    private String ifscCode;
    private double debitYourMoney;
}
