package com.finance.bharat.dto.upi;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UPIRequest {

    private String accountNumber;
    private String ifscCode;
    private String contactNumber;
    private String password;


}
