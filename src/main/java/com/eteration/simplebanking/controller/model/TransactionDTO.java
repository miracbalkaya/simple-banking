package com.eteration.simplebanking.controller.model;

import lombok.*;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDTO {
    private double amount;
    private String type;
    private String approvalCode;
    private LocalDateTime date;


}
