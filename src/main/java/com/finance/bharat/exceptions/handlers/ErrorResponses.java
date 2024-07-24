package com.finance.bharat.exceptions.handlers;

import com.finance.bharat.utils.AccountDetailsForExceptionHandler;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponses {

    private String errorMessage;
    private AccountDetailsForExceptionHandler status;
}
