package com.finance.bharat.mapper;

import com.finance.bharat.dto.account.AccountUpdateDetailsResponse;
import com.finance.bharat.entity.AccountInformation;

public class MapperToUpdateResponse {
    public AccountUpdateDetailsResponse userInformationToUpdateAccountResponse(AccountInformation accountInformation) {

        AccountUpdateDetailsResponse updateMapper = new AccountUpdateDetailsResponse();
        updateMapper.setAccountHolderName(accountInformation.getAccountHolderName());
        updateMapper.setAccountType(accountInformation.getAccountType());
        updateMapper.setContactEmail(accountInformation.getContactEmail());
        updateMapper.setContactPhone(accountInformation.getContactPhone());
        updateMapper.setContactAddress(accountInformation.getContactAddress());
        updateMapper.setStateOfOrigin(accountInformation.getStateOfOrigin());
        updateMapper.setPinCodeNumber(accountInformation.getPinCodeNumber());
        updateMapper.setCurrentLocation(accountInformation.getCurrentLocation());
        updateMapper.setDesignation(accountInformation.getDesignation());
        updateMapper.setCountry(accountInformation.getCountry());
        updateMapper.setAccountType(updateMapper.getAccountType());

        return updateMapper;
    }
}
