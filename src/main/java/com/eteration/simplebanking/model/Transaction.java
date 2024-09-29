package com.eteration.simplebanking.model;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "transaction_type", discriminatorType = DiscriminatorType.STRING)
public abstract class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime date;
    private double amount;
    private String approvalCode;
    @Column(name = "transaction_type", insertable = false, updatable = false)
    private String transactionType;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    public Transaction(double amount) {
        this.amount = amount;
        this.date = LocalDateTime.now();
        this.approvalCode = UUID.randomUUID().toString();
    }
}
