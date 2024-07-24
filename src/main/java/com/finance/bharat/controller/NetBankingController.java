package com.finance.bharat.controller;

import com.finance.bharat.dto.netBanking.GetNetBankingRequest;
import com.finance.bharat.dto.netBanking.NetBankingRequest;
import com.finance.bharat.dto.netBanking.NetBankingResponse;
import com.finance.bharat.service.upiAndNetBankingService.UPIAndNetBankingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("finance/bharat/netbanking")
public class NetBankingController {

    @Autowired
    private UPIAndNetBankingService upiAndNetBankingService;

    @PostMapping("/net-bankingId-create")
    ResponseEntity<NetBankingResponse> upiCreateUp(@RequestBody NetBankingRequest netBankingRequest){
        NetBankingResponse response = upiAndNetBankingService.createNetBanking(netBankingRequest);
        return new ResponseEntity<NetBankingResponse>(response , HttpStatus.ACCEPTED);
    }

    @GetMapping("/get-internetBanking-details")
    ResponseEntity<NetBankingResponse> getUpiDetails(@RequestBody GetNetBankingRequest netBankingRequest){
        NetBankingResponse response = upiAndNetBankingService.getYourNetBankingInfo(netBankingRequest);
        return new ResponseEntity<NetBankingResponse>(response , HttpStatus.ACCEPTED);
    }
}
