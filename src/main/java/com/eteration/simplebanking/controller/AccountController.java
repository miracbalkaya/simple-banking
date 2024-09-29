package com.eteration.simplebanking.controller;


import com.eteration.simplebanking.controller.model.AccountResponse;
import com.eteration.simplebanking.controller.model.TransactionRequest;
import com.eteration.simplebanking.controller.model.TransactionStatus;
import com.eteration.simplebanking.model.DepositTransaction;
import com.eteration.simplebanking.model.exception.InsufficientBalanceException;
import com.eteration.simplebanking.model.WithdrawalTransaction;
import com.eteration.simplebanking.services.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.AccountNotFoundException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/account/v1")
public class AccountController {

    private final AccountService accountService;

    @PostMapping("/{accountNumber}/credit")
    public ResponseEntity<TransactionStatus> credit(@PathVariable String accountNumber, @RequestBody DepositTransaction transaction) throws AccountNotFoundException, InsufficientBalanceException {

        String approvalCode = accountService.credit(accountNumber, transaction.getAmount());
        return ResponseEntity.ok(TransactionStatus.builder()
                .approvalCode(approvalCode)
                .status("OK")
                .build());
    }

    @PostMapping("/{accountNumber}/debit")
    public ResponseEntity<TransactionStatus> debit(@PathVariable String accountNumber, @RequestBody WithdrawalTransaction transaction) throws InsufficientBalanceException, AccountNotFoundException {
        String approvalCode = accountService.debit(accountNumber, transaction.getAmount());
        return ResponseEntity.ok(TransactionStatus.builder()
                .approvalCode(approvalCode)
                .status("OK")
                .build());
    }

    @PostMapping("/{accountNumber}/pay-bill")
    public ResponseEntity<TransactionStatus> payBill(@PathVariable String accountNumber, @RequestBody TransactionRequest request) throws InsufficientBalanceException, AccountNotFoundException {
        String approvalCode = accountService.payBill(accountNumber, request);
        return ResponseEntity.ok(TransactionStatus.builder()
                .approvalCode(approvalCode)
                .status("OK")
                .build());
    }

    @GetMapping("/{accountNumber}")
    public ResponseEntity<AccountResponse> getAccount(@PathVariable String accountNumber) throws AccountNotFoundException {
        AccountResponse account = accountService.getAccount(accountNumber);
        return ResponseEntity.ok(account);
    }

}
