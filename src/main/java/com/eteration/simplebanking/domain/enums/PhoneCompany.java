package com.eteration.simplebanking.domain.enums;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.AllArgsConstructor;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
@Schema(description = "Phone company options")
public enum PhoneCompany {
    @Schema(description = "Company A")
    COMPANY_A("Company A"),
    
    @Schema(description = "Company B")
    COMPANY_B("Company B"),
    
    @Schema(description = "Company C")
    COMPANY_C("Company C");
    
    private final String displayName;
} 