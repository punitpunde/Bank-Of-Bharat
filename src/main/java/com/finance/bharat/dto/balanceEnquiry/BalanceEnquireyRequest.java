package com.finance.bharat.dto.balanceEnquiry;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BalanceEnquireyRequest {

    private String accountNumber;
    private String password;
    private String ifscCode;

}
