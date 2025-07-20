package com.eteration.simplebanking.model.dto.request;

import com.eteration.simplebanking.domain.validation.annotation.AccountNumber;
import com.eteration.simplebanking.domain.validation.annotation.PhoneNumber;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "Request for creating a new bank account")
public record CreateAccountRequest(
    @Schema(description = "Account owner name", example = "John Doe", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "{validation.owner.required}")
    @Size(min = 2, max = 100, message = "{validation.owner.size}")
    String owner,

    @Schema(description = "Account number", example = "1234567890", requiredMode = Schema.RequiredMode.REQUIRED)
    @AccountNumber()
    String accountNumber
) {} 