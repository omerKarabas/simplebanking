package com.eteration.simplebanking.controller;

import com.eteration.simplebanking.model.dto.response.BankAccountResponse;
import com.eteration.simplebanking.model.dto.response.TransactionStatusResponse;
import com.eteration.simplebanking.model.dto.request.CreateAccountRequest;
import com.eteration.simplebanking.model.dto.request.TransactionRequest;
import com.eteration.simplebanking.model.dto.request.PhoneBillPaymentRequest;
import com.eteration.simplebanking.model.dto.request.CheckPaymentRequest;
import com.eteration.simplebanking.domain.enums.PhoneCompany;

import com.eteration.simplebanking.service.interfaces.BankingFacadeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/api/v1/bank-account")
@RequiredArgsConstructor
@Tag(name = "Bank Account", description = "Bank account management operations")
public class BankAccountController {

    private final BankingFacadeService bankingFacadeService;

    @PostMapping("/create")
    @Operation(summary = "Create a new bank account", description = "Creates a new bank account with the specified owner and account number")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Account created successfully",
                    content = @Content(schema = @Schema(implementation = BankAccountResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "409", description = "Account number already exists")
    })
    public ResponseEntity<BankAccountResponse> createBankAccount(@Valid @RequestBody CreateAccountRequest request) {
        BankAccountResponse result = bankingFacadeService.createBankAccount(request.owner(), request.accountNumber());
        return ResponseEntity.ok(result);
    }

    @PostMapping("/credit/{accountNumber}")
    @Operation(summary = "Credit money to account", description = "Adds money to the specified bank account")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Money credited successfully",
                    content = @Content(schema = @Schema(implementation = TransactionStatusResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "404", description = "Account not found")
    })
    public ResponseEntity<TransactionStatusResponse> credit(@Parameter(description = "Account number") @PathVariable String accountNumber,
                                                            @Valid @RequestBody TransactionRequest request) {
        TransactionStatusResponse result = bankingFacadeService.credit(accountNumber, request.amount());
        return ResponseEntity.ok(result);
    }

    @PostMapping("/debit/{accountNumber}")
    @Operation(summary = "Debit money from account", description = "Withdraws money from the specified bank account")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Money debited successfully",
                    content = @Content(schema = @Schema(implementation = TransactionStatusResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "404", description = "Account not found"),
            @ApiResponse(responseCode = "422", description = "Insufficient balance")
    })
    public ResponseEntity<TransactionStatusResponse> debit(@Parameter(description = "Account number") @PathVariable String accountNumber,
                                                           @Valid @RequestBody TransactionRequest request) {

        TransactionStatusResponse result = bankingFacadeService.debit(accountNumber, request.amount());
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{accountNumber}")
    @Operation(summary = "Get bank account details", description = "Retrieves the details of a bank account by account number")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Account details retrieved successfully",
                    content = @Content(schema = @Schema(implementation = BankAccountResponse.class))),
            @ApiResponse(responseCode = "404", description = "Account not found")
    })
    public ResponseEntity<BankAccountResponse> getBankAccount(@Parameter(description = "Account number") @PathVariable String accountNumber) {
        BankAccountResponse result = bankingFacadeService.getBankAccount(accountNumber);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/phone-bill-payment/{accountNumber}")
    @Operation(summary = "Pay phone bill", description = "Makes a phone bill payment from the specified bank account")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Phone bill payment successful",
                    content = @Content(schema = @Schema(implementation = TransactionStatusResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "404", description = "Account not found"),
            @ApiResponse(responseCode = "422", description = "Insufficient balance")
    })
    public ResponseEntity<TransactionStatusResponse> phoneBillPayment(@Parameter(description = "Account number") @PathVariable String accountNumber,
                                                                     @Valid @RequestBody PhoneBillPaymentRequest request) {
        TransactionStatusResponse result = bankingFacadeService.phoneBillPayment(accountNumber, request.phoneCompany(), request.phoneNumber(), request.amount());
        return ResponseEntity.ok(result);
    }

    @PostMapping("/check-payment/{accountNumber}")
    @Operation(summary = "Pay with check", description = "Makes a check payment from the specified bank account")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Check payment successful",
                    content = @Content(schema = @Schema(implementation = TransactionStatusResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "404", description = "Account not found"),
            @ApiResponse(responseCode = "422", description = "Insufficient balance")
    })
    public ResponseEntity<TransactionStatusResponse> checkPayment(@Parameter(description = "Account number") @PathVariable String accountNumber,
                                                                 @Valid @RequestBody CheckPaymentRequest request) {
        TransactionStatusResponse result = bankingFacadeService.checkPayment(accountNumber, request.payee(), request.amount());
        return ResponseEntity.ok(result);
    }
}