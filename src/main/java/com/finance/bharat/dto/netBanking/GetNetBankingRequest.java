package com.finance.bharat.dto.netBanking;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetNetBankingRequest {

    private String accountNumber;
    private String ifscCode;
}
