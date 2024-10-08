package com.finance.bharat.dto.balanceEnquiry;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BalanceEnquiryResponse {

    private String balanceId;
    private String accountNumber;
    private String statusMessage;
    private double yourBalance;
    private LocalDateTime localDateTime;
}
