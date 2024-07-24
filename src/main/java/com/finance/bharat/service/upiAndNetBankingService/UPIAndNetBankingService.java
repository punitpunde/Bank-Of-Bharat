package com.finance.bharat.service.upiAndNetBankingService;

import com.finance.bharat.dto.netBanking.GetNetBankingRequest;
import com.finance.bharat.dto.netBanking.NetBankingRequest;
import com.finance.bharat.dto.netBanking.NetBankingResponse;
import com.finance.bharat.dto.transferMoney.TransferMoneyRequest;
import com.finance.bharat.dto.transferMoney.TransferMoneyResponse;
import com.finance.bharat.dto.upi.GetUPIRequest;
import com.finance.bharat.dto.upi.UPIRequest;
import com.finance.bharat.dto.upi.UPIResponse;

public interface UPIAndNetBankingService {

    UPIResponse upiCreate(UPIRequest upiRequest);

    UPIResponse getYourUPIInformation(GetUPIRequest getUPIRequest);

    TransferMoneyResponse moneyTransferAccountToAccount(TransferMoneyRequest transferMoneyRequest);

    NetBankingResponse createNetBanking(NetBankingRequest netBankingRequest);

    NetBankingResponse getYourNetBankingInfo(GetNetBankingRequest getNetBankingRequest);
}
