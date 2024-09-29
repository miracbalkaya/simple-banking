package com.eteration.simplebanking.services.Impl;

import com.eteration.simplebanking.controller.model.AccountResponse;
import com.eteration.simplebanking.controller.model.TransactionRequest;
import com.eteration.simplebanking.model.*;
import com.eteration.simplebanking.model.enums.BillType;
import com.eteration.simplebanking.model.exception.InsufficientBalanceException;
import com.eteration.simplebanking.repository.AccountRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.security.auth.login.AccountNotFoundException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountServiceImplTest {

    @InjectMocks
    AccountServiceImpl accountServiceImpl;

    @Mock
    AccountRepository accountRepository;

    @Test
    public void testCredit() throws AccountNotFoundException, InsufficientBalanceException {
        Account account = mock(Account.class);
        when(accountRepository.findByAccountNumber(anyString())).thenReturn(Optional.of(account));
        when(account.deposit(anyDouble())).thenReturn("approvalCode123");

        String approvalCode = accountServiceImpl.credit("12345", 100.0);

        assertEquals("approvalCode123", approvalCode);
        verify(accountRepository, times(1)).save(account);
    }

    @Test
    public void testDebit() throws AccountNotFoundException, InsufficientBalanceException {

        Account account = mock(Account.class);
        when(accountRepository.findByAccountNumber(anyString())).thenReturn(Optional.of(account));
        when(account.withdraw(anyDouble())).thenReturn("approvalCode123");

        String approvalCode = accountServiceImpl.debit("12345", 100.0);

        assertEquals("approvalCode123", approvalCode);
        verify(accountRepository, times(1)).save(account);
    }

    @Test
    public void testGetAccount() throws AccountNotFoundException {
        Account account = new Account("Kerem Karaca", "12345");

        Transaction transaction = new WithdrawalTransaction(100.0);
        account.setTransactions(List.of(transaction));
        when(accountRepository.findByAccountNumber(anyString())).thenReturn(Optional.of(account));

        AccountResponse accountResponse = accountServiceImpl.getAccount("12345");

        assertEquals(account.getId(), accountResponse.getId());
        assertEquals(account.getOwner(), accountResponse.getOwner());
        assertEquals(account.getAccountNumber(), accountResponse.getAccountNumber());
        assertEquals(account.getBalance(), accountResponse.getBalance());

    }

    @Test
    public void testPayBill() throws AccountNotFoundException, InsufficientBalanceException {
        Account account = mock(Account.class);
        when(accountRepository.findByAccountNumber(anyString())).thenReturn(Optional.of(account));
        when(account.payBill(any(TransactionRequest.class))).thenReturn("approvalCode123");

        TransactionRequest request = new TransactionRequest(BillType.HGS_BILL, 100.0, "1234567890", "1234", "1234567890", "1234", "1234567890", "1234");
        String approvalCode = accountServiceImpl.payBill("12345", request);

        assertEquals("approvalCode123", approvalCode);
        verify(accountRepository, times(1)).save(account);
    }



}