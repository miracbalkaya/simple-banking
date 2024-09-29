package com.eteration.simplebanking.controller.model;


import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransactionStatus {

    private String status;
    private String approvalCode;
}
