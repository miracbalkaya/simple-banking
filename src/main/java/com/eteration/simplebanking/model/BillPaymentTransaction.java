package com.eteration.simplebanking.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public abstract class BillPaymentTransaction extends Transaction {

    private String payee;

    public BillPaymentTransaction(double amount, String payee) {
        super(amount);
        this.payee = payee;
        setTransactionType("BILL_PAYMENT");
    }
}
