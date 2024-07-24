package com.finance.bharat.service.upiAndNetBankingService;

import com.finance.bharat.constants.AccountDetailsConstants;
import com.finance.bharat.dto.netBanking.GetNetBankingRequest;
import com.finance.bharat.dto.netBanking.NetBankingRequest;
import com.finance.bharat.dto.netBanking.NetBankingResponse;
import com.finance.bharat.dto.transferMoney.TransferMoneyRequest;
import com.finance.bharat.dto.transferMoney.TransferMoneyResponse;
import com.finance.bharat.dto.upi.GetUPIRequest;
import com.finance.bharat.dto.upi.UPIRequest;
import com.finance.bharat.dto.upi.UPIResponse;
import com.finance.bharat.entity.AccountInformation;
import com.finance.bharat.entity.NetBankingInformation;
import com.finance.bharat.entity.UpiInformation;
import com.finance.bharat.exceptions.*;
import com.finance.bharat.notification.NotificationsUtility;
import com.finance.bharat.repository.AccountDetailsRepository;
import com.finance.bharat.repository.NetBankingRepository;
import com.finance.bharat.repository.UpiDetailsRepository;
import com.finance.bharat.utils.InternateBankingGenerator;
import com.finance.bharat.utils.UPIDGenerater;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class UPIAndNetBankingServiceImpl implements UPIAndNetBankingService {

    @Autowired
    private UpiDetailsRepository upiDetailsRepository;

    @Autowired
    private AccountDetailsRepository accountDetailsRepository;

    @Autowired
    private NetBankingRepository netBankingRepository;

    @Autowired
    private NotificationsUtility notificationsUtility;

    @Override
    public UPIResponse upiCreate(UPIRequest upiRequest) {
        AccountInformation accountInformation = accountDetailsRepository.findByAccountNumberAndIfscCode(upiRequest.getAccountNumber()
                , upiRequest.getIfscCode(), upiRequest.getPassword());

        NetBankingInformation existingNetBanking = netBankingRepository.findByAccountNumberAndIfscCode(upiRequest.getAccountNumber(), upiRequest.getIfscCode());

        if (accountInformation != null) {
            if (existingNetBanking == null) {
                notificationsUtility.sendForNetBankingCreationAlert();
                throw new NotHavingNetbanking("Account need to create a net banking ID first in order to be able to create a UPI ID.");
            }

            String isUpiPresentInAccount = accountInformation.getIsHaveUpiId();

            if (AccountDetailsConstants.BANK_NOTA_UPI_ID.equals(isUpiPresentInAccount)) {
                String UPI_GENERATED_ID = UUID.randomUUID().toString();

                UPIDGenerater upidGenerater = new UPIDGenerater();
                String UPI_ID = upidGenerater.generateUPID(accountInformation.getAccountHolderName());
                String UPI_CODE = upidGenerater.generatePin();

                UPIResponse upiResponse = new UPIResponse();
                upiResponse.setUPI_BALANCE(0.0);
                upiResponse.setContactEmail(accountInformation.getContactEmail());
                upiResponse.setUPI_CODE(UPI_CODE);
                upiResponse.setUpiId(UPI_ID);
                upiResponse.setAccountNumber(accountInformation.getAccountNumber());
                upiResponse.setContactNumber(accountInformation.getAccountNumber());
                upiResponse.setResponseMessage(AccountDetailsConstants.BANK_UPI_CREATED);

                UpiInformation upiInformation = new UpiInformation();
                upiInformation.setGlobalId(UPI_GENERATED_ID);
                upiInformation.setUPI_CODE(UPI_CODE);
                upiInformation.setUPI_BALANCE(0.0);
                upiInformation.setUpiId(UPI_ID);
                upiInformation.setContactEmail(accountInformation.getContactEmail());
                upiInformation.setIfscCode(accountInformation.getIfscCode());
                upiInformation.setContactNumber(accountInformation.getAccountNumber());
                upiInformation.setResponseMessage(AccountDetailsConstants.BANK_UPI_CREATED);
                upiInformation.setBankPassword(accountInformation.getPassword());
                upiInformation.setAccountNumber(accountInformation.getAccountNumber());
                upiDetailsRepository.save(upiInformation);

                accountInformation.setIsHaveUpiId("YES");
                accountDetailsRepository.save(accountInformation);

                notificationsUtility.sendForUPIIdCreation(UPI_ID);

                return upiResponse;
            } else {
                throw new UpiAlreadyExist("Your account number and IFSC code are already linked to a UPI ID");
            }
        }
        throw new AccountNotFoundStep("The details you have entered are incorrect. There is no account with these details. Please double-check the information and try again.");
    }

    @Override
    public UPIResponse getYourUPIInformation(GetUPIRequest getUPIRequest) {
        UpiInformation upiInformation = upiDetailsRepository.findByAccountNumberAndPassword(getUPIRequest.getAccountNumber()
                , getUPIRequest.getBankPassword());
        if (upiInformation != null) {

            UPIResponse response = new UPIResponse();
            response.setAccountNumber(upiInformation.getAccountNumber());
            response.setContactNumber(upiInformation.getContactNumber());
            response.setContactEmail(upiInformation.getContactEmail());
            response.setUpiId(upiInformation.getUpiId());
            response.setUPI_CODE(upiInformation.getUPI_CODE());
            response.setUPI_BALANCE(upiInformation.getUPI_BALANCE());
            response.setResponseMessage(upiInformation.getResponseMessage());
            return response;
        }
        throw new DetailsNotFountException("Details Not Found..");
    }

    @Override
    public TransferMoneyResponse moneyTransferAccountToAccount(TransferMoneyRequest transferMoneyRequest) {
        AccountInformation accountInformationForRecipient = accountDetailsRepository.findByAccountNumberAndName(
                transferMoneyRequest.getAccountNumberOfRecipient(), transferMoneyRequest.getFullNameOfRecipient());

        AccountInformation accountInformationForSender = accountDetailsRepository.findByAccountNumber(transferMoneyRequest.getAccountNumberOfSender());
        if (accountInformationForSender != null && accountInformationForRecipient != null) {
            double sendersMoney = transferMoneyRequest.getTransferAmount();
            if (sendersMoney >= 10000) {
                notificationsUtility.sendForHighAmountOfMoneyTransfer();
            }
            double sendersBankBalance = accountInformationForSender.getAccountBalance() - sendersMoney;

            accountInformationForSender.setAccountBalance(sendersBankBalance);
            accountDetailsRepository.save(accountInformationForSender);

            double recipientMoney = accountInformationForRecipient.getAccountBalance();
            double recipientBankBalance = sendersMoney + recipientMoney;

            accountInformationForRecipient.setAccountBalance(recipientBankBalance);
            accountDetailsRepository.save(accountInformationForRecipient);

            TransferMoneyResponse transferMoneyResponse = new TransferMoneyResponse();
            transferMoneyResponse.setResponseMessage(AccountDetailsConstants.MONEY_TRANSFERED_SUCCESSFULLY);
            return transferMoneyResponse;
        } else {
            throw new DetailsNotFountException("The details you have entered are incorrect. There is no account with these details. Please double-check the information and try again.");
        }
    }

    @Override
    public NetBankingResponse createNetBanking(NetBankingRequest netBankingRequest) {

        AccountInformation accountInformation = accountDetailsRepository.findByAccountNumberAndIfscCode(netBankingRequest.getAccountNumber(),
                netBankingRequest.getIfscCode(),
                netBankingRequest.getPassword());
        if (accountInformation != null) {

            NetBankingInformation existingNetBanking = netBankingRepository.findByAccountNumberAndIfscCode(netBankingRequest.getAccountNumber()
                    , netBankingRequest.getIfscCode());

            if (existingNetBanking != null) {
                InternateBankingGenerator internateBankingGenerator = new InternateBankingGenerator();
                NetBankingInformation netBankingInformation = new NetBankingInformation();
                NetBankingResponse netBankingResponse = new NetBankingResponse();

                netBankingResponse.setNetId(UUID.randomUUID().toString());
                netBankingResponse.setLocalDateTime(LocalDateTime.now());
                netBankingResponse.setAccountHolderName(accountInformation.getAccountHolderName());
                netBankingResponse.setAccountNumber(accountInformation.getAccountNumber());
                netBankingResponse.setIfscCode(accountInformation.getAccountNumber());
                netBankingResponse.setNet_BANKING_ID(internateBankingGenerator.generateInternetBankingId());

                netBankingInformation.setNetId(netBankingResponse.getNetId());
                netBankingInformation.setNET_BANKING_ID(netBankingResponse.getNet_BANKING_ID());
                netBankingInformation.setPassword(accountInformation.getPassword());
                netBankingInformation.setAccountNumber(accountInformation.getAccountNumber());
                netBankingInformation.setIfscCode(accountInformation.getIfscCode());
                netBankingInformation.setLocalDateTime(LocalDateTime.now());
                netBankingInformation.setAccountHolderName(accountInformation.getAccountHolderName());

                notificationsUtility.sendForNetBankingCreation();

                netBankingRepository.save(netBankingInformation);

                return netBankingResponse;
            }
            throw new NetBankingIdAlreadyExist("You already have a bank ID. If you've forgotten it, please contact our support team..");
        }
        throw new AccountNotFoundStep("The details you have entered are incorrect. There is no account with these details. Please double-check the information and try again.");
    }

    @Override
    public NetBankingResponse getYourNetBankingInfo(GetNetBankingRequest getNetBankingRequest) {

        NetBankingInformation netBankingInformation = netBankingRepository.findByAccountNumberAndIfscCode(
                getNetBankingRequest.getAccountNumber()
                , getNetBankingRequest.getIfscCode()
        );

        if (netBankingInformation != null){
            NetBankingResponse response = new NetBankingResponse();
            response.setNetId(netBankingInformation.getNetId());
            response.setAccountHolderName(netBankingInformation.getAccountHolderName());
            response.setAccountNumber(netBankingInformation.getAccountNumber());
            response.setIfscCode(netBankingInformation.getIfscCode());
            response.setLocalDateTime(netBankingInformation.getLocalDateTime());
            response.setNet_BANKING_ID(netBankingInformation.getNET_BANKING_ID());
            return response;
        }
        throw new DetailsNotFountException("Details Not Found..");
    }
}
