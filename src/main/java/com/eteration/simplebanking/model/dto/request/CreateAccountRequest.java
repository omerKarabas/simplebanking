package com.eteration.simplebanking.model.dto.request;

import com.eteration.simplebanking.domain.validation.annotations.AccountNumber;
import com.eteration.simplebanking.domain.validation.annotations.PhoneNumber;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateAccountRequest(
    @NotBlank(message = "{validation.owner.required}")
    @Size(min = 2, max = 100, message = "{validation.owner.size}")
    String owner,

    @AccountNumber(checkUniqueness = true, required = true)
    String accountNumber,

    @PhoneNumber(message = "{validation.phone.number.invalid}")
    String phoneNumber
) {} 