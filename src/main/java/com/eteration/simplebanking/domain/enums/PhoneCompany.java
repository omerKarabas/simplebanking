package com.eteration.simplebanking.domain.enums;

import lombok.Getter;
import lombok.AllArgsConstructor;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public enum PhoneCompany {
    COMPANY_A("Company A"),
    COMPANY_B("Company B"),
    COMPANY_C("Company C");
    
    private final String displayName;
} 