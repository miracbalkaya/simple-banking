package com.eteration.simplebanking.model;

import com.eteration.simplebanking.controller.model.TransactionRequest;

public class BillPaymentStrategy {

    public static BillPaymentTransaction payBill(TransactionRequest request) {
        switch (request.getBillType()) {
            case PHONE_BILL:
                PhoneBillPaymentTransaction phoneBillPaymentTransaction = new PhoneBillPaymentTransaction(request.getAmount(), request.getPayee());
                phoneBillPaymentTransaction.setPhoneNumber(request.getPhoneNumber());
                phoneBillPaymentTransaction.setOperator(request.getPayee());
                return phoneBillPaymentTransaction;
            case SGK_BILL:
                SGKBillPaymentTransaction sgkPaymentTransaction = new SGKBillPaymentTransaction(request.getAmount(), request.getPayee());
                sgkPaymentTransaction.setTcNo(request.getTcNo());
                sgkPaymentTransaction.setSgkNo(request.getSgkNo());
                return sgkPaymentTransaction;
            case HGS_BILL:
                HGSBillPaymentTransaction hgsPaymentTransaction = new HGSBillPaymentTransaction(request.getAmount(), request.getPayee());
                hgsPaymentTransaction.setPlateNumber(request.getPlateNumber());
                hgsPaymentTransaction.setPlateSerial(request.getPlateSerial());
                return hgsPaymentTransaction;
        }
        throw new IllegalArgumentException("Invalid bill type");
    }
}
