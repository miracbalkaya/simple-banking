package com.eteration.simplebanking.controller.model;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccountResponse {

    private Long id;
    private String owner;
    private String accountNumber;
    private double balance;
    private LocalDateTime createDate;
    private List<TransactionDTO> transactions;

    public AccountResponse(String owner,String accountNumber ){
        this.owner = owner;
        this.accountNumber = accountNumber;
    }
}
