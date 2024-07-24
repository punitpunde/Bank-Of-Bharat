package com.finance.bharat.controller;

import com.finance.bharat.dto.UserRequest;
import com.finance.bharat.dto.UserResponse;
import com.finance.bharat.dto.account.AccountDeleteAccountDetailsRequest;
import com.finance.bharat.dto.account.AccountDetailsResponse;
import com.finance.bharat.dto.account.AccountUpdateDetailsRequest;
import com.finance.bharat.dto.account.AccountUpdateDetailsResponse;
import com.finance.bharat.dto.balanceEnquiry.BalanceEnquireyRequest;
import com.finance.bharat.dto.balanceEnquiry.BalanceEnquiryResponse;
import com.finance.bharat.dto.credit.CreditCredential;
import com.finance.bharat.dto.credit.CreditResponse;
import com.finance.bharat.dto.debit.DebitCredential;
import com.finance.bharat.dto.debit.DebitedResponse;
import com.finance.bharat.dto.update.UpdateAmountManually;
import com.finance.bharat.dto.update.UpdateAmountResponse;
import com.finance.bharat.dto.upiPay.AddMoneyToAccountFromUPIRequest;
import com.finance.bharat.dto.upiPay.AddMoneyToAccountFromUPIResponse;
import com.finance.bharat.dto.upiPay.AddMoneyToUPIFromAccountRequest;
import com.finance.bharat.dto.upiPay.AddMoneyToUPIFromAccountRespose;
import com.finance.bharat.exceptions.AccountNotFoundStep;
import com.finance.bharat.service.AccountService;
import com.finance.bharat.utils.AccountDeletedSuccessResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("finance/bharat/")
public class AccountController {

   @Autowired
    private AccountService accountService;

   @PostMapping("/create-account")
    ResponseEntity<UserResponse> createYourAccount(@RequestBody UserRequest userRequest){
       UserResponse userResponse=accountService.createAccount(userRequest);
       return new ResponseEntity<UserResponse>(userResponse, HttpStatus.CREATED);
    }

    @PutMapping("/update-account-details")
    ResponseEntity<AccountUpdateDetailsResponse> updateAccountDetails(@RequestBody AccountUpdateDetailsRequest accountUpdateDetailsRequest){
       AccountUpdateDetailsResponse accountUpdateDetailsResponse=null;
       try{
           accountUpdateDetailsResponse=accountService.updateAccountDetails(accountUpdateDetailsRequest);
       }
       catch (AccountNotFoundStep e){
           throw new RuntimeException();
       }
       return new ResponseEntity<AccountUpdateDetailsResponse>(accountUpdateDetailsResponse,HttpStatus.CREATED);
    }

    @DeleteMapping("/delete-account")
    ResponseEntity<AccountDeletedSuccessResponse> deleteAccount(@RequestBody AccountDeleteAccountDetailsRequest accountDeleteAccountDetailsRequest){
       AccountDeletedSuccessResponse accountDeletedSuccessResponse=accountService.deleteAccount(accountDeleteAccountDetailsRequest);
       return new ResponseEntity<AccountDeletedSuccessResponse>(accountDeletedSuccessResponse,HttpStatus.ACCEPTED);
    }

    @GetMapping("/get-account-details/{accountNumber}/{IFSCCode}/{password}")
    ResponseEntity<AccountDetailsResponse> getAccountDetails(@PathVariable String accountNumber,
                                                             @PathVariable String IFSCCode,
                                                             @PathVariable String password){
       AccountDetailsResponse accountDetailsResponse = accountService.getYourAccountDetails(accountNumber,IFSCCode,password);
       return new ResponseEntity<AccountDetailsResponse>(accountDetailsResponse,HttpStatus.ACCEPTED);
    }

    @GetMapping("/balance-enquiry")
    ResponseEntity<BalanceEnquiryResponse> balanceEnquiry(@RequestBody BalanceEnquireyRequest balanceEnquireyRequest){
       BalanceEnquiryResponse balanceEnquiryResponse = accountService.balanceEnquiry(balanceEnquireyRequest);
       return new ResponseEntity<BalanceEnquiryResponse>(balanceEnquiryResponse,HttpStatus.ACCEPTED);
    }

    @GetMapping("/credit-money")
    ResponseEntity<CreditResponse> creditMoney(@RequestBody CreditCredential creditCredential){
       CreditResponse creditResponse = accountService.creditYourMoney(creditCredential);
       return new ResponseEntity<CreditResponse>(creditResponse,HttpStatus.ACCEPTED);
    }

    @GetMapping("/debit-money")
    ResponseEntity<DebitedResponse> debitMoney(@RequestBody DebitCredential debitCredential){
        DebitedResponse debitedResponse = accountService.debitYourMoney(debitCredential);
        return new ResponseEntity<DebitedResponse>(debitedResponse,HttpStatus.ACCEPTED);
    }

    @PostMapping("/add-money-to-account-from-upi")
    ResponseEntity<AddMoneyToAccountFromUPIResponse> addMoneyToAccountFromUPI(AddMoneyToAccountFromUPIRequest addMoneyToAccountFromUPIRequest){
       AddMoneyToAccountFromUPIResponse addMoneyToAccountFromUPIResponse = accountService.addMoneyToAccountFromUPI(addMoneyToAccountFromUPIRequest);
       return new ResponseEntity<AddMoneyToAccountFromUPIResponse>(addMoneyToAccountFromUPIResponse,HttpStatus.ACCEPTED);
    }

    @PostMapping("/pay-money-from-upi")
    ResponseEntity<AddMoneyToUPIFromAccountRespose> payFromUpiId(AddMoneyToUPIFromAccountRequest addMoneyToUPIFromAccountRequest){
       AddMoneyToUPIFromAccountRespose addMoneyToUPIFromAccountRespose = accountService.payUsingApi(addMoneyToUPIFromAccountRequest);
       return new ResponseEntity<AddMoneyToUPIFromAccountRespose>(addMoneyToUPIFromAccountRespose,HttpStatus.ACCEPTED);
    }

    @PutMapping("/update/money")
    ResponseEntity<UpdateAmountResponse> addMoneyInPerson(@RequestBody UpdateAmountManually updateAmountManually){
        UpdateAmountResponse response = accountService.updateAmountInPerson(updateAmountManually);
        return new ResponseEntity<UpdateAmountResponse>(response , HttpStatus.ACCEPTED);
    }
}
