package com.finance.bharat.dto.netBanking;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NetBankingRequest {

    private String accountNumber;
    private String accountHolderName;
    private String ifscCode;
    private String password;

}
