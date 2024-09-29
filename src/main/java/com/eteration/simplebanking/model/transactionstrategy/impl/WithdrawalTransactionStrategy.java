package com.eteration.simplebanking.model.transactionstrategy.impl;

import com.eteration.simplebanking.model.Account;
import com.eteration.simplebanking.model.exception.InsufficientBalanceException;
import com.eteration.simplebanking.model.Transaction;
import com.eteration.simplebanking.model.transactionstrategy.TransactionStrategy;

public class WithdrawalTransactionStrategy implements TransactionStrategy {
    @Override
    public void apply(Account account, Transaction transaction) throws InsufficientBalanceException {
        if (account.getBalance() >= transaction.getAmount()) {
            account.setBalance(account.getBalance() - transaction.getAmount());
        } else {
            throw new InsufficientBalanceException("Yetersiz bakiye.");
        }
    }
}
