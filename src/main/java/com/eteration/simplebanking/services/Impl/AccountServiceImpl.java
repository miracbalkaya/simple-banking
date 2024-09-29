package com.eteration.simplebanking.services.Impl;

import com.eteration.simplebanking.controller.model.AccountResponse;
import com.eteration.simplebanking.controller.model.TransactionDTO;
import com.eteration.simplebanking.controller.model.TransactionRequest;
import com.eteration.simplebanking.model.Account;
import com.eteration.simplebanking.model.exception.InsufficientBalanceException;
import com.eteration.simplebanking.repository.AccountRepository;
import com.eteration.simplebanking.services.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.security.auth.login.AccountNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    @Override
    public String credit(String accountNumber, double amount) throws AccountNotFoundException, InsufficientBalanceException {
        Account account = findAccount(accountNumber);
        String approvalCode = account.deposit(amount);
        accountRepository.save(account);
        return approvalCode;
    }

    @Override
    public String debit(String accountNumber, double amount) throws InsufficientBalanceException, AccountNotFoundException {
        Account account = findAccount(accountNumber);
        String approvalCode = account.withdraw(amount);
        accountRepository.save(account);
        return approvalCode;
    }

    @Override
    public String payBill(String accountNumber, TransactionRequest request) throws InsufficientBalanceException, AccountNotFoundException {
        Account account = findAccount(accountNumber);
        String approvalCode = account.payBill(request);
        accountRepository.save(account);
        return approvalCode;
    }

    @Override
    public AccountResponse getAccount(String accountNumber) throws AccountNotFoundException {
        Account account = findAccount(accountNumber);
        List<TransactionDTO> transactions = account.getTransactions()
                .stream()
                .map(transaction -> TransactionDTO.builder()
                        .amount(transaction.getAmount())
                        .type(transaction.getTransactionType())
                        .approvalCode(transaction.getApprovalCode())
                        .date(transaction.getDate())
                        .build())
                .collect(Collectors.toList());

        return AccountResponse.builder()
                .id(account.getId())
                .owner(account.getOwner())
                .accountNumber(account.getAccountNumber())
                .balance(account.getBalance())
                .createDate(account.getCreateDate())
                .transactions(transactions)
                .build();
    }

    @Override
    public Account findAccount(String accountNumber) throws AccountNotFoundException {
        return accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new AccountNotFoundException("Account not found: " + accountNumber));
    }
}
