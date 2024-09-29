package com.eteration.simplebanking;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.eteration.simplebanking.controller.AccountController;
import com.eteration.simplebanking.controller.model.TransactionStatus;
import com.eteration.simplebanking.controller.model.AccountResponse;
import com.eteration.simplebanking.model.DepositTransaction;
import com.eteration.simplebanking.model.exception.InsufficientBalanceException;
import com.eteration.simplebanking.model.WithdrawalTransaction;
import com.eteration.simplebanking.services.AccountService;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;


@Disabled
@SpringBootTest
@ContextConfiguration
@AutoConfigureMockMvc
class ControllerTests  {

    @Spy
    @InjectMocks
    private AccountController controller;
 
    @Mock
    private AccountService service;

    
    @Test
    public void givenId_Credit_thenReturnJson()
    throws Exception {
        
        AccountResponse account = new AccountResponse("Kerem Karaca", "17892");

        doReturn(account).when(service).getAccount( "17892");
        ResponseEntity<TransactionStatus> result = controller.credit( "17892", new DepositTransaction(1000.0));
        verify(service, times(1)).getAccount("17892");
        assertEquals("OK", result.getBody().getStatus());
    }

    @Test
    public void givenId_CreditAndThenDebit_thenReturnJson()
    throws Exception {
        
        AccountResponse account = new AccountResponse("Kerem Karaca", "17892");

        DepositTransaction depositTransaction = new DepositTransaction(1000.0);
        WithdrawalTransaction withdrawalTransaction = new WithdrawalTransaction(50.0);

        doReturn(account).when(service).credit( "17892", depositTransaction.getAmount());
        doReturn(account) .when(service).debit( "17892", withdrawalTransaction.getAmount());

        ResponseEntity<TransactionStatus> result = controller.credit( "17892", depositTransaction);
        ResponseEntity<TransactionStatus> result2 = controller.debit( "17892", withdrawalTransaction);

        verify(service, times(2)).getAccount("17892");
        assertEquals("OK", result.getBody().getStatus());
        assertEquals("OK", result2.getBody().getStatus());
        assertEquals(950.0, account.getBalance(),0.001);
    }

    @Test
    public void givenId_CreditAndThenDebitMoreGetException_thenReturnJson()
    throws Exception {
        Assertions.assertThrows( InsufficientBalanceException.class, () -> {
            AccountResponse account = new AccountResponse("Kerem Karaca", "17892");

            doReturn(account).when(service).getAccount( "17892");
            ResponseEntity<TransactionStatus> result = controller.credit( "17892", new DepositTransaction(1000.0));
            assertEquals("OK", result.getBody().getStatus());
            assertEquals(1000.0, account.getBalance(),0.001);
            verify(service, times(1)).getAccount("17892");

            ResponseEntity<TransactionStatus> result2 = controller.debit( "17892", new WithdrawalTransaction(5000.0));
        });
    }

    @Test
    public void givenId_GetAccount_thenReturnJson()
    throws Exception {
        
        AccountResponse account = new AccountResponse("Kerem Karaca", "17892");

        doReturn(account).when(service).getAccount( "17892");
        ResponseEntity<AccountResponse> result = controller.getAccount( "17892");
        verify(service, times(1)).getAccount("17892");
        assertEquals(account, result.getBody());
    }

}
