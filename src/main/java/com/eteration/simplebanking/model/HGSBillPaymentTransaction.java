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
@DiscriminatorValue("HGS_BILL_PAYMENT")
public class HGSBillPaymentTransaction extends BillPaymentTransaction{
    private String plateNumber;
    private String plateSerial;

    public HGSBillPaymentTransaction(double amount, String payee) {
        super(amount, payee);
    }
}
