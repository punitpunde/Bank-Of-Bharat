package com.finance.bharat.controller;

import com.finance.bharat.dto.upi.GetUPIRequest;
import com.finance.bharat.dto.upi.UPIRequest;
import com.finance.bharat.dto.upi.UPIResponse;
import com.finance.bharat.service.upiAndNetBankingService.UPIAndNetBankingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("finance/bharat/upi")
public class UpiController {

    @Autowired
    private UPIAndNetBankingService upiAndNetBankingService;

    @PostMapping("/upi-create")
    ResponseEntity<UPIResponse> createUPID(@RequestBody UPIRequest upiRequest) {
        UPIResponse upiResponse = upiAndNetBankingService.upiCreate(upiRequest);
        return new ResponseEntity<>(upiResponse, HttpStatus.ACCEPTED);
    }

    @GetMapping("/get-upi-details")
    ResponseEntity<UPIResponse> getUpiDetails(GetUPIRequest getUPIRequest) {
        UPIResponse upiResponse = upiAndNetBankingService.getYourUPIInformation(getUPIRequest);
        return new ResponseEntity<>(upiResponse, HttpStatus.ACCEPTED);
    }


}
