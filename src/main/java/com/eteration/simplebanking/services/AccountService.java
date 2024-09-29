package com.eteration.simplebanking.services;

import com.eteration.simplebanking.controller.model.AccountResponse;
import com.eteration.simplebanking.controller.model.TransactionRequest;
import com.eteration.simplebanking.model.Account;
import com.eteration.simplebanking.model.exception.InsufficientBalanceException;

import javax.security.auth.login.AccountNotFoundException;

public interface AccountService {

    String credit(String accountNumber, double amount) throws AccountNotFoundException, InsufficientBalanceException;
    String debit(String accountNumber, double amount) throws InsufficientBalanceException, AccountNotFoundException;
    String payBill(String accountNumber, TransactionRequest request) throws InsufficientBalanceException, AccountNotFoundException;

    AccountResponse getAccount(String accountNumber) throws AccountNotFoundException ;

    Account findAccount(String accountNumber) throws AccountNotFoundException;
}
