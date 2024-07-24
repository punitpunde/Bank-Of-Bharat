package com.finance.bharat.dto.upi;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetUPIRequest {

    private String accountNumber;
    private String bankPassword;

}
