package com.finance.bharat.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AccountDetailsForExceptionHandler {

    PENDING("Pending"),
    APPROVED("Approved"),
    REJECTED("Rejected"),
    COMPLETED("Completed");

    private final String label;

    public String getLabel() {
        return label;
    }

}
