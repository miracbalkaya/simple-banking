package com.eteration.simplebanking.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Getter
@Setter
@NoArgsConstructor
@Entity
@DiscriminatorValue("SGK_BILL_PAYMENT")
public class SGKBillPaymentTransaction extends BillPaymentTransaction{
    private String tcNo;
    private String sgkNo;

    public SGKBillPaymentTransaction(double amount, String payee) {
        super(amount, payee);
    }
}
