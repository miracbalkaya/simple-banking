package com.eteration.simplebanking;


import com.eteration.simplebanking.controller.model.TransactionRequest;
import com.eteration.simplebanking.model.*;

import com.eteration.simplebanking.model.enums.BillType;
import com.eteration.simplebanking.model.exception.InsufficientBalanceException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ModelTest {

    @Test
    public void testCreateAccountAndSetBalance0() {
        Account account = new Account("Kerem Karaca", "17892");
        assertTrue(account.getOwner().equals("Kerem Karaca"));
        assertTrue(account.getAccountNumber().equals("17892"));
        assertTrue(account.getBalance() == 0);
    }

    @Test
    public void testDepositIntoBankAccount() throws InsufficientBalanceException {
        Account account = new Account("Demet Demircan", "9834");
        account.deposit(100);
        assertTrue(account.getBalance() == 100);
    }

    @Test
    public void testWithdrawFromBankAccount() throws InsufficientBalanceException {
        Account account = new Account("Demet Demircan", "9834");
        account.deposit(100);
        assertTrue(account.getBalance() == 100);
        account.withdraw(50);
        assertTrue(account.getBalance() == 50);
    }

    @Test
    public void testWithdrawException() {
        Assertions.assertThrows(InsufficientBalanceException.class, () -> {
            Account account = new Account("Demet Demircan", "9834");
            account.deposit(100);
            account.withdraw(500);
        });

    }

    @Test
    public void testTransactions() throws InsufficientBalanceException {
        // Create account
        Account account = new Account("Canan Kaya", "1234");
        assertTrue(account.getTransactions().size() == 0);

        // Deposit Transaction
        DepositTransaction depositTrx = new DepositTransaction(100);
        assertTrue(depositTrx.getDate() != null);
        account.post(depositTrx);
        assertTrue(account.getBalance() == 100);
        assertTrue(account.getTransactions().size() == 1);

        // Withdrawal Transaction
        WithdrawalTransaction withdrawalTrx = new WithdrawalTransaction(60);
        assertTrue(withdrawalTrx.getDate() != null);
        account.post(withdrawalTrx);
        assertTrue(account.getBalance() == 40);
        assertTrue(account.getTransactions().size() == 2);
    }

    @Test
    public void testPayBillException() {
        //given
        Account account = new Account("Canan Kaya", "1234");
        account.setBalance(10d);

        TransactionRequest request = new TransactionRequest();
        request.setBillType(BillType.PHONE_BILL);
        request.setAmount(50);

        // when & then
        InsufficientBalanceException thrown = Assertions.assertThrows(InsufficientBalanceException.class, () -> {
            account.payBill(request);
        });

        Assertions.assertEquals("Yetersiz bakiye.", thrown.getMessage());
    }

    @Test
    public void testPhonePayBill() throws InsufficientBalanceException {
        //given
        Account account = new Account("Canan Kaya", "1234");
        account.setBalance(100d);

        TransactionRequest request = new TransactionRequest();
        request.setAmount(50);
        request.setBillType(BillType.PHONE_BILL);
        request.setPhoneNumber("45678");
        request.setPayee("Vodafone");

        //when
        String approvalCode = account.payBill(request);

        //then
        assertNotNull(approvalCode);
        assertTrue(account.getBalance() == 50);
        assertEquals(1, account.getTransactions().size());
        assertEquals(PhoneBillPaymentTransaction.class, account.getTransactions().get(0).getClass());
    }

    @Test
    public void testHgsPayBill() throws InsufficientBalanceException {
        //given
        Account account = new Account("Canan Kaya", "1235");
        account.setBalance(100d);

        TransactionRequest request = new TransactionRequest();
        request.setAmount(50);
        request.setBillType(BillType.HGS_BILL);
        request.setPlateNumber("34ABC123");
        request.setPlateSerial("13456");

        //when
        String approvalCode = account.payBill(request);

        //then
        assertNotNull(approvalCode);
        assertTrue(account.getBalance() == 50);
        assertEquals(1, account.getTransactions().size());
        assertEquals(HGSBillPaymentTransaction.class, account.getTransactions().get(0).getClass());
    }

    @Test
    public void testSgkPayBill() throws InsufficientBalanceException {
        //given
        Account account = new Account("Canan Kaya", "1236");
        account.setBalance(100d);


        TransactionRequest request = new TransactionRequest();
        request.setAmount(50);
        request.setBillType(BillType.SGK_BILL);
        request.setTcNo("12345678901");
        request.setSgkNo("12345678901");

        //when
        String approvalCode = account.payBill(request);

        //then
        assertNotNull(approvalCode);
        assertTrue(account.getBalance() == 50);
        assertEquals(1, account.getTransactions().size());
        assertEquals(SGKBillPaymentTransaction.class, account.getTransactions().get(0).getClass());
    }

}
