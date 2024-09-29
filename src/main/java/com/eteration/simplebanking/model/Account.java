package com.eteration.simplebanking.model;


import com.eteration.simplebanking.controller.model.TransactionRequest;
import com.eteration.simplebanking.model.exception.InsufficientBalanceException;
import com.eteration.simplebanking.model.transactionstrategy.TransactionFactory;
import com.eteration.simplebanking.model.transactionstrategy.TransactionStrategy;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "accounts")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String owner;
    private String accountNumber;
    private double balance;
    private LocalDateTime createDate;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "account")
    private List<Transaction> transactions = new ArrayList<>();

    public Account(String owner, String accountNumber) {
        this.owner = owner;
        this.accountNumber = accountNumber;
        this.balance = 0.0;
        this.createDate = LocalDateTime.now();
    }

    public void post(Transaction transaction) throws InsufficientBalanceException {
        TransactionStrategy strategy = TransactionFactory.getTransactionStrategy(transaction.getTransactionType());
        strategy.apply(this, transaction);

        transaction.setAccount(this);
        this.transactions.add(transaction);
    }

    public String deposit(double amount) throws InsufficientBalanceException {
        DepositTransaction depositTransaction = new DepositTransaction(amount);
        post(depositTransaction);
        return depositTransaction.getApprovalCode();
    }

    public String withdraw(double amount) throws InsufficientBalanceException {
        WithdrawalTransaction withdrawalTransaction = new WithdrawalTransaction(amount);
        post(withdrawalTransaction);
        return withdrawalTransaction.getApprovalCode();
    }

    public String payBill(TransactionRequest request) throws InsufficientBalanceException {
        BillPaymentTransaction billPaymentTransaction = BillPaymentStrategy.payBill(request);
        post(billPaymentTransaction);
        return billPaymentTransaction.getApprovalCode();
    }
}
