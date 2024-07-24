package com.finance.bharat.mapper;

import com.finance.bharat.dto.transaction.TransactionResponse;
import com.finance.bharat.entity.TransactionDetailsHistory;

public class TransactionToTransactionResponse {

    public static TransactionResponse transactionToResponse(TransactionDetailsHistory transactionDetailsHistory) {

        TransactionResponse updateMapper = new TransactionResponse();
        updateMapper.setAccountId(transactionDetailsHistory.getTransactionId());
        updateMapper.setDebitedOrCredited(transactionDetailsHistory.getDebitedOrCredited());
        updateMapper.setAccountNumber(transactionDetailsHistory.getAccountNumber());
        updateMapper.setLocalDateTime(transactionDetailsHistory.getLocalDateTime());
        updateMapper.setDebitOrCreditMoney(transactionDetailsHistory.getDebitOrCreditMoney());
        return updateMapper;
    }
}
