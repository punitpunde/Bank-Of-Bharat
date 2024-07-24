package com.finance.bharat.dto.account;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountDeleteAccountDetailsResponse {

    private String userDeleteId;
    private String Message;
}
