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
@DiscriminatorValue("PHONE_BILL_PAYMENT")
public class PhoneBillPaymentTransaction extends BillPaymentTransaction{
    private String phoneNumber;
    private String operator;

    public PhoneBillPaymentTransaction(double amount, String payee) {
        super(amount, payee);
    }
}
