package com.eteration.simplebanking.model;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

// This class is a place holder you can change the complete implementation
@Entity
@NoArgsConstructor
@Getter
@Setter
@DiscriminatorValue("DEPOSIT")
public class DepositTransaction extends Transaction {

    public DepositTransaction(double amount) {
        super(amount);
        setTransactionType("DEPOSIT");
    }
}
