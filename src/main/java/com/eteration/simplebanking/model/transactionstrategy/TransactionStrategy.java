package com.eteration.simplebanking.model.transactionstrategy;

import com.eteration.simplebanking.model.Account;
import com.eteration.simplebanking.model.exception.InsufficientBalanceException;
import com.eteration.simplebanking.model.Transaction;

public interface TransactionStrategy {

    void apply(Account account, Transaction transaction) throws InsufficientBalanceException;

}
