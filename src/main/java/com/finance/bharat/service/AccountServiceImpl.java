package com.finance.bharat.service;

import com.finance.bharat.constants.AccountDetailsConstants;
import com.finance.bharat.constants.TwilioConfig;
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
import com.finance.bharat.dto.transaction.TransactionRequest;
import com.finance.bharat.dto.update.UpdateAmountManually;
import com.finance.bharat.dto.update.UpdateAmountResponse;
import com.finance.bharat.dto.upiPay.AddMoneyToAccountFromUPIRequest;
import com.finance.bharat.dto.upiPay.AddMoneyToAccountFromUPIResponse;
import com.finance.bharat.dto.upiPay.AddMoneyToUPIFromAccountRequest;
import com.finance.bharat.dto.upiPay.AddMoneyToUPIFromAccountRespose;
import com.finance.bharat.entity.AccountInformation;
import com.finance.bharat.entity.NetBankingInformation;
import com.finance.bharat.entity.UpiInformation;
import com.finance.bharat.exceptions.AccountNotFoundStep;
import com.finance.bharat.exceptions.EmailAlreadyExistStep;
import com.finance.bharat.exceptions.InSufficientBalance;
import com.finance.bharat.exceptions.PhoneNumberAlreadyExistStep;
import com.finance.bharat.mapper.MapperToResponse;
import com.finance.bharat.mapper.MapperToUpdateResponse;
import com.finance.bharat.notification.NotificationsUtility;
import com.finance.bharat.repository.AccountDetailsRepository;
import com.finance.bharat.repository.UpiDetailsRepository;
import com.finance.bharat.service.transactionService.TransactionService;
import com.finance.bharat.utils.AccountDeletedSuccessResponse;
import com.finance.bharat.utils.AccountDetailsGenarators;
import com.finance.bharat.utils.AccountLimitReached;
import com.twilio.Twilio;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountDetailsRepository accountDetailsRepository;
    @Autowired
    private NotificationsUtility notificationsUtility;
    @Autowired
    private AccountLimitReached accountLimitReached;
    @Autowired
    private TransactionService transactionService;
    @Autowired
    private UpiDetailsRepository upiDetailsRepository;

    static {
        Twilio.init(TwilioConfig.TWILIO_ACCOUNT_SID, TwilioConfig.TWILIO_AUTH_TOKEN);
    }

    @Override
    public UserResponse createAccount(UserRequest userRequest) {
        log.info("Creating a new Account");
        String userIdGenerated = UUID.randomUUID().toString();
        MapperToResponse mapperToResponse = new MapperToResponse();

        Optional<AccountInformation> existingEmail = Optional.ofNullable(accountDetailsRepository.findByContactEmail(userRequest.getContactEmail()));
        Optional<AccountInformation> existingPhone = Optional.ofNullable(accountDetailsRepository.findByContactEmail(userRequest.getContactPhone()));

        if (existingEmail.isPresent()) {
            throw new EmailAlreadyExistStep("Email Already Exist. ");
        } else if (existingPhone.isPresent()) {
            throw new PhoneNumberAlreadyExistStep("Phone Already Exist. ");
        } else {

            AccountDetailsGenarators accountDetailsGenarators = new AccountDetailsGenarators();

            String IFSC_CODE = accountDetailsGenarators.generateIFSC();
            String BANK_PIN = accountDetailsGenarators.generateBankCode();
            String PASSWORD = accountDetailsGenarators.generatePin();

            AccountInformation accountInformation = AccountInformation.builder()
                    .accountId(userIdGenerated)
                    .accountHolderName(userRequest.getAccountHolderName())
                    .contactEmail(userRequest.getContactEmail())
                    .contactPhone(userRequest.getContactPhone())
                    .gender(userRequest.getGender())
                    .isHaveUpiId(AccountDetailsConstants.BANK_NAME)
                    .contactAddress(userRequest.getContactAddress())
                    .stateOfOrigin(userRequest.getStateOfOrigin())
                    .pinCodeNumber(userRequest.getPinCodeNumber())
                    .currentLocation(userRequest.getCurrentLocation())
                    .designation(userRequest.getDesignation())
                    .country(userRequest.getCountry())
                    .password(PASSWORD)
                    .bankName(AccountDetailsConstants.BANK_NAME)
                    .bankBranch(AccountDetailsConstants.BANK_BRANCH)
                    .routingNumber(AccountDetailsConstants.BANK_ROUTING)
                    .accountType(userRequest.getAccountType())
                    .status(AccountDetailsConstants.BANK_STATUS)
                    .localDateTime(LocalDateTime.now())
                    .accountOpenData(LocalDate.now())
                    .accountNumber(accountDetailsGenarators.generateAccountNumber())
                    .ifscCode("BOBI" + IFSC_CODE)
                    .bankPinCode(BANK_PIN)
                    .build();

            notificationsUtility.sendForTwilionLogin();
            notificationsUtility.sendForCreateAccountNotification(accountInformation.getAccountNumber());
            accountDetailsRepository.save(accountInformation);

            UserResponse userResponse = mapperToResponse.userInformationToUserResponse(accountInformation);
            userResponse.setMessage(AccountDetailsConstants.BANK_ACCOUNT_CREATED);
            log.info("Account created successfully for user: {}", userResponse.getAccountHolderName());

            return userResponse;

        }

    }

    @Override
    public AccountUpdateDetailsResponse updateAccountDetails(AccountUpdateDetailsRequest accountUpdateDetailsRequest) {
        log.info("Updating account details for account number: {}", accountUpdateDetailsRequest.getAccountNumber());
        Optional<AccountInformation> userInformation = Optional.ofNullable(accountDetailsRepository.findByAccountNumber(accountUpdateDetailsRequest.getAccountNumber()));

        if (userInformation.isPresent()) {
            log.info("User information found for account number: {}", accountUpdateDetailsRequest.getAccountNumber());
            AccountInformation updateAccountInformation = AccountInformation.builder()
                    .accountHolderName(accountUpdateDetailsRequest.getAccountHolderName())
                    .contactEmail(accountUpdateDetailsRequest.getContactEmail())
                    .contactPhone(accountUpdateDetailsRequest.getContactPhone())
                    .accountType(accountUpdateDetailsRequest.getAccountType())
                    .stateOfOrigin(accountUpdateDetailsRequest.getStateOfOrigin())
                    .contactAddress(accountUpdateDetailsRequest.getContactAddress())
                    .pinCodeNumber(accountUpdateDetailsRequest.getPinCodeNumber())
                    .currentLocation(accountUpdateDetailsRequest.getCurrentLocation())
                    .designation(accountUpdateDetailsRequest.getDesignation())
                    .country(accountUpdateDetailsRequest.getCountry())
                    .localDateTime(LocalDateTime.now())
                    .build();

            log.info("Account details updated successfully for account holder: {}", accountUpdateDetailsRequest.getAccountHolderName());
            notificationsUtility.sendForUpdateAccountDetails(accountUpdateDetailsRequest.getAccountNumber());
            accountDetailsRepository.save(updateAccountInformation);
            MapperToUpdateResponse mapperToUpdateResponse = new MapperToUpdateResponse();

            return mapperToUpdateResponse.userInformationToUpdateAccountResponse(updateAccountInformation);

        } else {
            log.info("Account not found for account number: {}", accountUpdateDetailsRequest.getAccountNumber());
            throw new AccountNotFoundStep("The details you have entered are incorrect. There is no account with these details. Please double-check the information and try again.");
        }

    }

    @Override
    public AccountDeletedSuccessResponse deleteAccount(AccountDeleteAccountDetailsRequest accountDeleteAccountDetailsRequest) {
        log.info("Deleting account with account number: {}", accountDeleteAccountDetailsRequest.getAccountNumber());

        AccountInformation accountInformation = accountDetailsRepository.findByAccountNumberAndIfscCode(
                accountDeleteAccountDetailsRequest.getAccountNumber()
                , accountDeleteAccountDetailsRequest.getIfscCode()
                , accountDeleteAccountDetailsRequest.getPassword()
        );

        if (accountInformation != null) {
            log.info("Account found for deletion: {}", accountInformation);

            accountDetailsRepository.delete(accountInformation);
            log.info("Account deleted for account number: {}", accountDeleteAccountDetailsRequest.getAccountNumber());

            notificationsUtility.sendForDeletedAccount();
            log.info("Account deletion notification sent");

            return new AccountDeletedSuccessResponse("Account deleted successfully.");

        } else {
            log.info("Account not found for deletion. Account details: AccountNumber={}, ContactEmail={}, Password={}",
                    accountDeleteAccountDetailsRequest.getAccountNumber(), accountDeleteAccountDetailsRequest.getContactEmail(), accountDeleteAccountDetailsRequest.getPassword());
            throw new AccountNotFoundStep("The details you have entered are incorrect. There is no account with these details. Please double-check the information and try again.");
        }

    }

    @Override
    public AccountDetailsResponse getYourAccountDetails(String accountNumber, String IFSCCode, String password) {
        log.info("Getting account details for accountNumber: {}", accountNumber);

        AccountInformation accountInformation = accountDetailsRepository.findByAccountNumberAndIfscCode(accountNumber, IFSCCode, password);


        if (accountInformation != null) {
            log.info("Account Details found for account number {}", accountNumber);
            AccountDetailsResponse accountDetailsResponse = new AccountDetailsResponse();

            accountDetailsResponse.setAccountId(accountInformation.getAccountId());
            accountDetailsResponse.setAccountHolderName(accountInformation.getAccountHolderName());
            accountDetailsResponse.setAccountNumber(accountInformation.getAccountNumber());
            accountDetailsResponse.setIfscCode(accountInformation.getIfscCode());
            accountDetailsResponse.setAccountType(accountInformation.getAccountType());
            accountDetailsResponse.setCountry(accountInformation.getCountry());
            accountDetailsResponse.setGender(accountInformation.getGender());
            accountDetailsResponse.setBankBranch(accountInformation.getBankBranch());
            accountDetailsResponse.setBankName(accountInformation.getBankName());
            accountDetailsResponse.setContactEmail(accountInformation.getContactEmail());
            accountDetailsResponse.setContactAddress(accountInformation.getContactAddress());
            accountDetailsResponse.setContactPhone(accountInformation.getContactPhone());
            accountDetailsResponse.setStateOfOrigin(accountInformation.getStateOfOrigin());
            accountDetailsResponse.setPinCodeNumber(accountInformation.getPinCodeNumber());
            accountDetailsResponse.setCurrentLocation(accountInformation.getCurrentLocation());
            accountDetailsResponse.setAccountOpenDate(accountInformation.getAccountOpenData());
            accountDetailsResponse.setLocalDateTime(LocalDateTime.now());
            accountDetailsResponse.setAccountBalance(accountInformation.getAccountBalance());
            accountDetailsResponse.setRoutingNumber(accountInformation.getRoutingNumber());
            accountDetailsResponse.setStatus(accountInformation.getStatus());

            NetBankingInformation netBankingInformation = new NetBankingInformation();

            accountDetailsResponse.setNet_banking_password(netBankingInformation.getPassword());
            accountDetailsResponse.setNet_BANKING_ID(netBankingInformation.getNET_BANKING_ID());

            UpiInformation upiInformation = new UpiInformation();

            accountDetailsResponse.setUPI_ID(upiInformation.getUpiId());
            accountDetailsResponse.setUPI_BALANCE(upiInformation.getUPI_BALANCE());

            return accountDetailsResponse;
        } else {

            log.info("Account not found for accountNumber: {}", accountNumber);
            throw new AccountNotFoundStep("The details you have entered are incorrect. There is no account with these details. Please double-check the information and try again.");

        }

    }

    @Override
    public BalanceEnquiryResponse balanceEnquiry(BalanceEnquireyRequest balanceEnquireyRequest) {
        log.info("Received request for balance enquiry for accountNumber: {}", balanceEnquireyRequest.getAccountNumber());

        AccountInformation accountInformation = accountDetailsRepository.findByAccountNumberAndIfscCode(
                balanceEnquireyRequest.getAccountNumber(),
                balanceEnquireyRequest.getIfscCode(),
                balanceEnquireyRequest.getPassword()
        );

        if (accountInformation != null) {
            log.info("Account information found for the request.");

            notificationsUtility.sendForBalanceEnquiry(accountInformation.getAccountNumber()
                    , accountInformation.getAccountBalance()
                    , accountInformation.getContactPhone());

            log.info("Sent balance enquiry notification.");

            String balanceId = UUID.randomUUID().toString();
            BalanceEnquiryResponse balanceEnquiryResponse = new BalanceEnquiryResponse();
            balanceEnquiryResponse.setBalanceId(balanceId);
            balanceEnquiryResponse.setAccountNumber(accountInformation.getAccountNumber());
            balanceEnquiryResponse.setYourBalance(accountInformation.getAccountBalance());
            balanceEnquiryResponse.setLocalDateTime(LocalDateTime.now());
            balanceEnquiryResponse.setStatusMessage(AccountDetailsConstants.BANK_ACCOUNT_BALANCE_STATUS);

            log.info("Balance enquiry successful.");

            return balanceEnquiryResponse;
        } else {
            log.info("Account information not found for the request.");
            throw new AccountNotFoundStep("The details you have entered are incorrect. There is no account with these details. Please double-check the information and try again.");
        }

    }

    @Override
    public CreditResponse creditYourMoney(CreditCredential creditCredential) {
        log.info("Crediting money for account number: {}", creditCredential.getAccountNumber());

        AccountInformation accountInformation = accountDetailsRepository.findByAccountNumberAndIfscCode(
                creditCredential.getAccountNumber(),
                creditCredential.getIfscCode(),
                creditCredential.getPassword());
        if (accountInformation != null) {
            log.info("Account found for crediting money. Account Number: {}", creditCredential.getAccountNumber());
            accountLimitReached.validateDailyTransactionLimit(accountInformation);

            double creditedAmount = creditCredential.getCreditYourMoney();
            double currentBalance = accountInformation.getAccountBalance() + creditedAmount;

            accountInformation.setAccountBalance(currentBalance);
            accountInformation = accountDetailsRepository.save(accountInformation);


            notificationsUtility.sendForCredit(accountInformation.getAccountNumber(), accountInformation.getAccountBalance(), accountInformation.getContactPhone());

            String autoGeneratedId = UUID.randomUUID().toString();

            CreditResponse addingMoney = new CreditResponse();

            TransactionRequest transactionRequest = new TransactionRequest();

            transactionRequest.setTransactionId(autoGeneratedId);
            transactionRequest.setDebitedOrCredited(AccountDetailsConstants.BANK_CREDIT);
            transactionRequest.setAccountNumber(accountInformation.getAccountNumber());
            transactionRequest.setDebitOrCreditMoney(creditedAmount);
            transactionRequest.setIfscCode(accountInformation.getIfscCode());
            transactionRequest.setLocalDateTime(LocalDateTime.now());

            transactionService.saveTransaction(transactionRequest);

            addingMoney.setCreditId(autoGeneratedId);
            addingMoney.setAccountNumber(accountInformation.getAccountNumber());
            addingMoney.setBankName(accountInformation.getBankName());
            addingMoney.setAccountHolderName(accountInformation.getAccountHolderName());
            addingMoney.setIfscCode(accountInformation.getIfscCode());
            addingMoney.setCurrentBalance(currentBalance);
            addingMoney.setLocalDateTime(LocalDateTime.now());
            addingMoney.setStatusMoney(AccountDetailsConstants.BANK_ACCOUNT_BALANCE_CREDIT);

            log.info("Money credited successfully for account number: {}. Current balance: {}", creditCredential.getAccountNumber(), currentBalance);

            return addingMoney;
        } else {
            log.info("Account not found for crediting money. Account details: AccountNumber={}, IFSCCode={}, Password={}",
                    creditCredential.getAccountNumber(), creditCredential.getIfscCode(), creditCredential.getPassword());

            throw new AccountNotFoundStep("The details you have entered are incorrect. There is no account with these details. Please double-check the information and try again.");
        }
    }

    @Override
    public DebitedResponse debitYourMoney(DebitCredential debitCredential) {

        log.info("Request for debiting the money found for account number {}", debitCredential.getAccountNumber());

        AccountInformation accountInformation = accountDetailsRepository.findByAccountNumberAndIfscCode(debitCredential.getAccountNumber()
                , debitCredential.getIfscCode()
                , debitCredential.getPassword());
        if (accountInformation != null) {
            log.info("Account found for debiting money. Account Number: {}", debitCredential.getAccountNumber());

            double debitedAmount = debitCredential.getDebitYourMoney();
            accountLimitReached.validateDailyTransactionLimit(accountInformation);

            if (debitedAmount >= 10000) {
                log.info("High amount of money transfer. Account Balance: {}, Debited Amount: {}", accountInformation.getAccountBalance(), debitedAmount);
                notificationsUtility.sendForHighAmountOfMoneyTransfer();
            }
            double currentBalance = accountInformation.getAccountBalance();
            if (currentBalance >= debitedAmount) {
                currentBalance = currentBalance - debitedAmount;

                accountInformation.setAccountBalance(currentBalance);
                accountDetailsRepository.save(accountInformation);

                notificationsUtility.sendForDebitedAccount(accountInformation.getAccountNumber()
                        , accountInformation.getAccountBalance()
                        , accountInformation.getContactPhone());

                String autoGeneratedId = UUID.randomUUID().toString();
                TransactionRequest transactionRequest = new TransactionRequest();
                transactionRequest.setTransactionId(autoGeneratedId);
                transactionRequest.setAccountNumber(accountInformation.getAccountNumber());
                transactionRequest.setDebitedOrCredited(AccountDetailsConstants.BANK_DEBIT);
                transactionRequest.setDebitOrCreditMoney(debitedAmount);
                transactionRequest.setIfscCode(accountInformation.getIfscCode());
                transactionRequest.setLocalDateTime(LocalDateTime.now());

                transactionService.saveTransaction(transactionRequest);

                DebitedResponse debitedResponse = new DebitedResponse();
                debitedResponse.setDebitedId(autoGeneratedId);
                debitedResponse.setAccountNumber(accountInformation.getAccountNumber());
                debitedResponse.setIfscCode(accountInformation.getIfscCode());
                debitedResponse.setAccountHolderName(accountInformation.getAccountHolderName());
                debitedResponse.setLocalDateTime(LocalDateTime.now());
                debitedResponse.setStatusDebit(AccountDetailsConstants.BANK_ACCOUNT_BALANCE_DEBITED);
                debitedResponse.setCurrentBalance(currentBalance);
                debitedResponse.setDebitYourMoney(debitedAmount);

                log.info("Money debited successfully for account number: {}. Current balance: {}", debitCredential.getAccountNumber(), currentBalance);

                return debitedResponse;

            } else {
                log.info("Insufficient balance to complete the transaction. Account Balance: {}, Debited Amount: {}", accountInformation.getAccountBalance(), debitedAmount);
                throw new InSufficientBalance("Insufficient balance to complete the transaction.");
            }
        } else {
            log.info("Account not found for debiting money. Account details: AccountNumber={}, IFSCCode={}, Password={}",
                    debitCredential.getAccountNumber(), debitCredential.getIfscCode(), debitCredential.getPassword());
            throw new AccountNotFoundStep("The details you have entered are incorrect. There is no account with these details. Please double-check the information and try again.");
        }
    }

    @Override
    public AddMoneyToAccountFromUPIResponse addMoneyToAccountFromUPI(AddMoneyToAccountFromUPIRequest addMoneyToAccountFromUPIRequest) {
        log.info("Adding money from account number {} to UPI {}.", addMoneyToAccountFromUPIRequest.getAccountNumber(), addMoneyToAccountFromUPIRequest.getUpiId());

        AccountInformation accountInformation = accountDetailsRepository.findByAccountNumberAndPassword(addMoneyToAccountFromUPIRequest.getAccountNumber()
                , addMoneyToAccountFromUPIRequest.getPassword());

        UpiInformation upiInformation = upiDetailsRepository.findByUpiId(addMoneyToAccountFromUPIRequest.getUpiId());

        if (accountInformation != null && upiInformation != null) {
            log.info("Account and UPI found for the transaction.");

            accountLimitReached.validateDailyTransactionLimit(accountInformation);

            double moneyGetFromMainAccount = accountInformation.getAccountBalance();
            double addedMoneyFromUPI = addMoneyToAccountFromUPIRequest.getAddedFromUpi();
            double totalMoney = moneyGetFromMainAccount + addedMoneyFromUPI;

            accountInformation.setAccountBalance(totalMoney);

            accountDetailsRepository.save(accountInformation);

            AddMoneyToAccountFromUPIResponse addMoneyToAccountFromUPIResponse = new AddMoneyToAccountFromUPIResponse();
            addMoneyToAccountFromUPIResponse.setStatus(AccountDetailsConstants.SUCCESS_ADD_MONEY_TO_ACCOUNT_FROM_UPI);

            log.info("Money added successfully from account number {} to UPI {}.", addMoneyToAccountFromUPIRequest.getAccountNumber(), addMoneyToAccountFromUPIRequest.getUpiId());

            return addMoneyToAccountFromUPIResponse;

        }
        log.info("Account or UPI not found for the transaction. AccountNumber={}, UPIId={}", addMoneyToAccountFromUPIRequest.getAccountNumber(), addMoneyToAccountFromUPIRequest.getUpiId());

        throw new AccountNotFoundStep("The details you have entered are incorrect. There is no account with these details. Please double-check the information and try again.");
    }

    @Override
    public AddMoneyToUPIFromAccountRespose payUsingApi(AddMoneyToUPIFromAccountRequest addMoneyToUPIFromAccountRequest) {
        log.info("Received request to pay using UPI: {}", addMoneyToUPIFromAccountRequest);

        AccountInformation accountInformation = accountDetailsRepository.findByAccountNumber(addMoneyToUPIFromAccountRequest.getAccountNumber());
        UpiInformation upiInformation = upiDetailsRepository.findByUpiId(addMoneyToUPIFromAccountRequest.getUpiId());

        if (upiInformation != null && accountInformation != null) {
            log.info("UPI and account information found for the request.");
            accountLimitReached.validateDailyTransactionLimit(accountInformation);

            if (accountInformation.getAccountBalance() > addMoneyToUPIFromAccountRequest.getPayMoney()) {
                double amountToPay = addMoneyToUPIFromAccountRequest.getPayMoney();
                double accountAmount = accountInformation.getAccountBalance();
                double moneyLeftForMainAccount = accountAmount - amountToPay;

                accountInformation.setAccountBalance(moneyLeftForMainAccount);
                accountDetailsRepository.save(accountInformation);

                upiInformation.setUPI_BALANCE(amountToPay);
                upiDetailsRepository.save(upiInformation);

                AddMoneyToUPIFromAccountRespose addMoneyToUPIFromAccountRespose = new AddMoneyToUPIFromAccountRespose();
                addMoneyToUPIFromAccountRespose.setStatus(AccountDetailsConstants.SUCCESS_STATUS);
                addMoneyToUPIFromAccountRespose.setResponseMessage(AccountDetailsConstants.SUCCESS_PAY_MONEY_FROM_UPI);

                log.info("Payment using UPI successful.");

                return addMoneyToUPIFromAccountRespose;
            } else {
                log.error("Insufficient balance for the payment.");
                throw new InSufficientBalance("Insufficient Balance..");
            }

        } else {
            log.info("Account or UPI not found for the transaction. AccountNumber={}, UPIId={}", addMoneyToUPIFromAccountRequest.getAccountNumber(), addMoneyToUPIFromAccountRequest.getUpiId());
            throw new AccountNotFoundStep("The details you have entered are incorrect. There is no account with these details. Please double-check the information and try again.");
        }
    }

    @Override
    public UpdateAmountResponse updateAmountInPerson(UpdateAmountManually updateAmountManually) {
        log.info("Updating amount in person for account number: {}", updateAmountManually.getAccountNumber());

        AccountInformation accountInformation = accountDetailsRepository.findByAccountNumber(updateAmountManually.getAccountNumber());

        if (accountInformation != null) {
            log.info("Account found for updating amount in person. Account Number: {}", updateAmountManually.getAccountNumber());

            accountInformation.setAccountBalance(updateAmountManually.getAccountBalance());
            accountDetailsRepository.save(accountInformation);

            log.info("Account balance updated in person. New balance: {}", updateAmountManually.getAccountBalance());
        } else {

            log.info("Account not found for updating amount in person. Account Number: {}", updateAmountManually.getAccountNumber());

            throw new AccountNotFoundStep("The details you have entered are incorrect. " +
                    "There is no account with these details. Please double-check the information and try again.");
        }
        UpdateAmountResponse updateAmountResponse = new UpdateAmountResponse();
        updateAmountResponse.setMessage("In person, an ATM has credited your account, adding a physical touch to your financial update.");
        return updateAmountResponse;
    }

}











