package com.eteration.simplebanking.model.transactionstrategy.impl;

import com.eteration.simplebanking.model.Account;
import com.eteration.simplebanking.model.Transaction;
import com.eteration.simplebanking.model.transactionstrategy.TransactionStrategy;

public class DepositTransactionStrategy implements TransactionStrategy {
    @Override
    public void apply(Account account, Transaction transaction) {
        account.setBalance(account.getBalance() + transaction.getAmount());
    }
}
