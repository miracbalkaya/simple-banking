package com.eteration.simplebanking.controller.model;

import com.eteration.simplebanking.model.enums.BillType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransactionRequest {

    private BillType billType;
    private double amount;
    private String payee;
    private String phoneNumber;
    private String tcNo;
    private String sgkNo;
    private String plateNumber;
    private String plateSerial;
}
