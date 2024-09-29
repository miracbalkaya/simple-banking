package com.eteration.simplebanking.controller;

import com.eteration.simplebanking.controller.model.AccountResponse;
import com.eteration.simplebanking.controller.model.TransactionRequest;
import com.eteration.simplebanking.controller.model.TransactionStatus;
import com.eteration.simplebanking.model.enums.BillType;
import com.eteration.simplebanking.model.DepositTransaction;
import com.eteration.simplebanking.model.exception.InsufficientBalanceException;
import com.eteration.simplebanking.model.WithdrawalTransaction;
import com.eteration.simplebanking.services.AccountService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;

import javax.security.auth.login.AccountNotFoundException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
@ContextConfiguration
@AutoConfigureMockMvc
class AccountControllerTest {

    @InjectMocks
    AccountController accountController;

    @Mock
    AccountService accountService;


    @Test
    void testCredit() throws AccountNotFoundException, InsufficientBalanceException {
        // given
        when(accountService.credit(anyString(), any(Double.class))).thenReturn("approvalCode123");

        // when
        DepositTransaction transaction = new DepositTransaction(100.0);
        ResponseEntity<TransactionStatus> response = accountController.credit("12345", transaction);

        // then
        assertEquals("OK", response.getBody().getStatus());
        assertEquals("approvalCode123", response.getBody().getApprovalCode());
    }

    @Test
    public void testDebit() throws AccountNotFoundException, InsufficientBalanceException {
        // given
        when(accountService.debit(anyString(), any(Double.class))).thenReturn("approval");

        // when
        WithdrawalTransaction transaction = new WithdrawalTransaction(100.0);
        ResponseEntity<TransactionStatus> response = accountController.debit("12345", transaction);

        // then
        assertEquals("OK", response.getBody().getStatus());
        assertEquals("approval", response.getBody().getApprovalCode());

    }

    @Test
    public void testGetAccount() throws AccountNotFoundException {
        // given
        AccountResponse accountResponse = new AccountResponse("Kerem Karaca", "17892");
        when(accountService.getAccount(anyString())).thenReturn(accountResponse);

        // when
        ResponseEntity<AccountResponse> response = accountController.getAccount("17892");

        // then
        assertEquals("Kerem Karaca", response.getBody().getOwner());
        assertEquals("17892", response.getBody().getAccountNumber());
    }

    @Test
    public void testPayBill() throws InsufficientBalanceException, AccountNotFoundException {
        when(accountService.payBill(anyString(), any(TransactionRequest.class))).thenReturn("approvalCode123");

        TransactionRequest request = new TransactionRequest(BillType.HGS_BILL, 100.0, "1234567890", "1234", "1234567890", "1234", "1234567890", "1234");
        ResponseEntity<TransactionStatus> response = accountController.payBill("12345", request);

        assertEquals("OK", response.getBody().getStatus());
        assertEquals("approvalCode123", response.getBody().getApprovalCode());

    }
}