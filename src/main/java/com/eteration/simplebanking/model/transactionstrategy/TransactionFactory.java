package com.eteration.simplebanking.model.transactionstrategy;

import com.eteration.simplebanking.model.transactionstrategy.impl.BillPaymentTransactionStrategy;
import com.eteration.simplebanking.model.transactionstrategy.impl.DepositTransactionStrategy;
import com.eteration.simplebanking.model.transactionstrategy.impl.WithdrawalTransactionStrategy;

public class TransactionFactory {
    public static TransactionStrategy getTransactionStrategy(String transactionType) {
        switch (transactionType) {
            case "DEPOSIT":
                return new DepositTransactionStrategy();
            case "WITHDRAWAL":
                return new WithdrawalTransactionStrategy();
            case "BILL_PAYMENT":
                return new BillPaymentTransactionStrategy();
            default:
                throw new IllegalArgumentException("Unknown transaction type: " + transactionType);
        }
    }
}
