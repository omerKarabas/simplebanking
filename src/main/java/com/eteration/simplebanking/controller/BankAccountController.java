package com.eteration.simplebanking.controller;

import com.eteration.simplebanking.model.dto.response.BankAccountResponse;
import com.eteration.simplebanking.model.dto.response.TransactionStatusResponse;
import com.eteration.simplebanking.model.dto.request.CreateAccountRequest;
import com.eteration.simplebanking.model.dto.request.TransactionRequest;
import com.eteration.simplebanking.model.dto.request.PhoneBillPaymentRequest;
import com.eteration.simplebanking.model.dto.request.CheckPaymentRequest;
import com.eteration.simplebanking.domain.enums.PhoneCompany;

import com.eteration.simplebanking.service.interfaces.BankingFacadeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/bank-account/v1")
@RequiredArgsConstructor
public class BankAccountController {

    private final BankingFacadeService bankingFacadeService;

    @PostMapping("/create")
    public ResponseEntity<BankAccountResponse> createBankAccount(@RequestBody CreateAccountRequest request) {
        BankAccountResponse result = bankingFacadeService.createBankAccount(request.owner(), request.accountNumber());
        return ResponseEntity.ok(result);
    }

    @PostMapping("/credit/{accountNumber}")
    public ResponseEntity<TransactionStatusResponse> credit(@PathVariable String accountNumber,
                                                            @RequestBody TransactionRequest request) {
        TransactionStatusResponse result = bankingFacadeService.credit(accountNumber, request.amount());
        return ResponseEntity.ok(result);
    }

    @PostMapping("/debit/{accountNumber}")
    public ResponseEntity<TransactionStatusResponse> debit(@PathVariable String accountNumber,
                                                           @RequestBody TransactionRequest request) {

        TransactionStatusResponse result = bankingFacadeService.debit(accountNumber, request.amount());
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{accountNumber}")
    public ResponseEntity<BankAccountResponse> getBankAccount(@PathVariable String accountNumber) {
        BankAccountResponse result = bankingFacadeService.getBankAccount(accountNumber);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/phone-bill-payment/{accountNumber}")
    public ResponseEntity<TransactionStatusResponse> phoneBillPayment(@PathVariable String accountNumber,
                                                                     @RequestBody PhoneBillPaymentRequest request) {
        TransactionStatusResponse result = bankingFacadeService.phoneBillPayment(accountNumber, request.phoneCompany(), request.phoneNumber(), request.amount());
        return ResponseEntity.ok(result);
    }

    @PostMapping("/check-payment/{accountNumber}")
    public ResponseEntity<TransactionStatusResponse> checkPayment(@PathVariable String accountNumber,
                                                                 @RequestBody CheckPaymentRequest request) {
        TransactionStatusResponse result = bankingFacadeService.checkPayment(accountNumber, request.payee(), request.amount());
        return ResponseEntity.ok(result);
    }
}