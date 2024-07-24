package com.finance.bharat.service;

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
import com.finance.bharat.utils.AccountDeletedSuccessResponse;

public interface AccountService {
    UserResponse createAccount(UserRequest userRequest);

    AccountUpdateDetailsResponse updateAccountDetails(AccountUpdateDetailsRequest accountUpdateDetailsRequest);

    AccountDeletedSuccessResponse deleteAccount(AccountDeleteAccountDetailsRequest accountDeleteAccountDetailsRequest);

    AccountDetailsResponse getYourAccountDetails(String accountNumber, String IFSCCode, String password);

    BalanceEnquiryResponse balanceEnquiry(BalanceEnquireyRequest balanceEnquireyRequest);

    CreditResponse creditYourMoney(CreditCredential creditCredential);

    DebitedResponse debitYourMoney(DebitCredential debitCredential);

    AddMoneyToAccountFromUPIResponse addMoneyToAccountFromUPI(AddMoneyToAccountFromUPIRequest addMoneyToUPIFromAccountRequest);

    AddMoneyToUPIFromAccountRespose payUsingApi(AddMoneyToUPIFromAccountRequest addMoneyToUPIFromAccountRequest);

    UpdateAmountResponse updateAmountInPerson(UpdateAmountManually updateAmountManually);
}
