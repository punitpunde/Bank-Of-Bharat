package com.finance.bharat.exceptions.handlers;

import com.finance.bharat.exceptions.*;
import com.finance.bharat.utils.AccountDetailsForExceptionHandler;
import com.twilio.exception.ApiException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandling {

    @ExceptionHandler(EmailAlreadyExistStep.class)
    ResponseEntity<ErrorResponses> emailAlreadyExistException(EmailAlreadyExistStep ex) {
        ErrorResponses errorResponses = new ErrorResponses();
        errorResponses.setStatus(AccountDetailsForExceptionHandler.REJECTED);
        errorResponses.setErrorMessage(ex.getMessage());
        return new ResponseEntity<>(errorResponses, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(PhoneNumberAlreadyExistStep.class)
    ResponseEntity<ErrorResponses> phoneAlreadyExistException(PhoneNumberAlreadyExistStep ex) {
        ErrorResponses errorResponses = new ErrorResponses();
        errorResponses.setStatus(AccountDetailsForExceptionHandler.REJECTED);
        errorResponses.setErrorMessage(ex.getMessage());
        return new ResponseEntity<>(errorResponses, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(DailyLimitExceed.class)
    ResponseEntity<ErrorResponses> phoneAlreadyExistException(DailyLimitExceed ex) {
        ErrorResponses errorResponses = new ErrorResponses();
        errorResponses.setErrorMessage(ex.getMessage());
        errorResponses.setStatus(AccountDetailsForExceptionHandler.REJECTED);
        return new ResponseEntity<>(errorResponses, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(DetailsNotFountException.class)
    ResponseEntity<ErrorResponses> detailsNotFoundException(DetailsNotFountException ex) {
        ErrorResponses errorResponses = new ErrorResponses();
        errorResponses.setErrorMessage(ex.getMessage());
        errorResponses.setStatus(AccountDetailsForExceptionHandler.REJECTED);
        return new ResponseEntity<>(errorResponses, HttpStatus.FORBIDDEN);
    }

//    @ExceptionHandler(UpiNotFoundException.class)
//    ResponseEntity<ErrorResponses> upInotFound(UpiNotFoundException ex) {
//        ErrorResponses errorResponses = new ErrorResponses();
//        errorResponses.setErrorMessage(ex.getMessage());
//        errorResponses.setStatus(REJECTED);
//        return new ResponseEntity<>(errorResponses, HttpStatus.FORBIDDEN);
//    }

    @ExceptionHandler(NetBankingIdAlreadyExist.class)
    ResponseEntity<ErrorResponses> netBankingIdAlreadyExistException(NetBankingIdAlreadyExist ex) {
        ErrorResponses errorResponses = new ErrorResponses();
        errorResponses.setErrorMessage(ex.getMessage());
        errorResponses.setStatus(AccountDetailsForExceptionHandler.REJECTED);
        return new ResponseEntity<>(errorResponses, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(NotHavingNetbanking.class)
    ResponseEntity<ErrorResponses> netBankingAccountNotPresent(NotHavingNetbanking ex) {
        ErrorResponses errorResponses = new ErrorResponses();
        errorResponses.setErrorMessage(ex.getMessage());
        errorResponses.setStatus(AccountDetailsForExceptionHandler.REJECTED);
        return new ResponseEntity<>(errorResponses, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(InSufficientBalance.class)
    ResponseEntity<ErrorResponses> inSufficientFunds(InSufficientBalance ex) {
        ErrorResponses errorResponses = new ErrorResponses();
        errorResponses.setErrorMessage(ex.getMessage());
        errorResponses.setStatus(AccountDetailsForExceptionHandler.REJECTED);
        return new ResponseEntity<>(errorResponses, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(UpiAlreadyExist.class)
    ResponseEntity<ErrorResponses> upiAlreadyExist(UpiAlreadyExist ex) {
        ErrorResponses errorResponses = new ErrorResponses();
        errorResponses.setErrorMessage(ex.getMessage());
        errorResponses.setStatus(AccountDetailsForExceptionHandler.REJECTED);
        return new ResponseEntity<>(errorResponses, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(ApiException.class)
    ResponseEntity<ApiException> apiExceptionHandling(ApiException ex) {
        ErrorResponses errorResponses = new ErrorResponses();
        errorResponses.setErrorMessage(ex.getMessage());
        errorResponses.setStatus(AccountDetailsForExceptionHandler.REJECTED);
        return new ResponseEntity<ApiException>(HttpStatus.CREATED);
    }

    @ExceptionHandler(AccountNotFoundStep.class)
    ResponseEntity<ErrorResponses> accountNotFound(AccountNotFoundStep ex) {
        ErrorResponses errorResponses = new ErrorResponses();
        errorResponses.setErrorMessage(ex.getMessage());
        errorResponses.setStatus(AccountDetailsForExceptionHandler.REJECTED);
        return new ResponseEntity<>(errorResponses, HttpStatus.FORBIDDEN);
    }

//    @ExceptionHandler(AccountBalanceMinimumSteps.class)
//    ResponseEntity<ErrorResponses> accountBalanceExist(AccountBalanceMinimumSteps ex) {
//        ErrorResponses errorResponses = new ErrorResponses();
//        errorResponses.setErrorMessage(ex.getMessage());
//        errorResponses.setStatus(REJECTED);
//        return new ResponseEntity<>(errorResponses, HttpStatus.FORBIDDEN);
//    }

}
