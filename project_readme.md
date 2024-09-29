# Simple Banking Project

## Table of Contents
- [Overview](#overview)
- [Features](#features)
- [Technologies Used](#technologies-used)
- [Project Setup](#project-setup)
- [Usage](#usage)
- [API Endpoints](#api-endpoints)
- [Design Patterns](#design-patterns)
- [Testing](#testing)

## Overview

The **Simple Banking Project** is a RESTful API built with Spring Boot, designed to manage bank accounts and handle operations like deposits, withdrawals, and bill payments. The project is developed using clean coding principles and implements modern software design patterns like **Factory Pattern** and **Strategy Pattern** for handling different transaction types.

## Features

- **Deposit and Withdrawal**: Support for credit and debit transactions.
- **Bill Payment**: Pay bills such as HGS, SGK, and phone bills.
- **Transaction History**: Retrieve all transactions associated with an account.
- **Approval Codes**: Every transaction generates a unique approval code.

## Technologies Used

- **Java 17**
- **Spring Boot**: Framework for building RESTful APIs.
- **Hibernate / JPA**: For ORM and database interactions.
- **MySQL Database**:  MySQL Database: RDBMS is a database. Can be used optionally (H2 database, oracle, etc.)
- **Lombok**: To reduce boilerplate code.
- **JUnit & Mockito**: For writing and running unit tests.

## API Endpoints

### 1. Credit Account (Deposit Money)
- **URL**: `/account/v1/{accountNumber}/credit`
- **Method**: POST
- **Request Body**:
  ```json
  {
    "amount": 1000.0
  }
  ```
- **Response**:
  ```json
  {
    "approvalCode": "APPROVAL_CODE",
    "status": "OK"
  }
  ```
- **Description**: Credits (deposits) the specified amount to the account.

### 2. Debit Account (Withdraw Money)
- **URL**: `/account/v1/{accountNumber}/debit`
- **Method**: POST
- **Request Body**:
  ```json
  {
    "amount": 500.0
  }
  ```
- **Response**:
  ```json
  {
    "approvalCode": "APPROVAL_CODE",
    "status": "OK"
  }
  ```
- **Description**: Debits (withdraws) the specified amount from the account. Throws `InsufficientBalanceException` if the balance is insufficient.

### 3. Pay Bill
- **URL**: `/account/v1/{accountNumber}/pay-bill`
- **Method**: POST
- **Request Body**:
  ```json
  {
    "billType":"HGS_BILL",
    "amount": 200.0,
    "payee":"hgs",
    "plateNumber":"34AC545",
    "plateSerial":"45634567"
  }
  ```
- **Response**:
  ```json
  {
    "approvalCode": "APPROVAL_CODE",
    "status": "OK"
  }
  ```
- **Description**: Pays a bill from the account. Throws `InsufficientBalanceException` if the balance is insufficient.


### 4. Get Account Details
- **URL**: `/account/v1/{accountNumber}`
- **Method**: GET
- **Response**:
  ```json
  {
    "owner": "John Doe",
    "accountNumber": "123456",
    "balance": 1500.0,
    "createdDate": "2023-09-25T15:00:00",
    "transactions": [
      {
        "transactionId": 1,
        "type": "DEPOSIT",
        "amount": 1000.0,
        "date": "2023-09-25T15:30:00"
      },
      {
        "transactionId": 2,
        "type": "WITHDRAWAL",
        "amount": 500.0,
        "date": "2023-09-25T16:30:00"
      }
    ]
  }
  ```
- **Description**: Retrieves the account details, including balance and transaction history.

1. Projeyi klonlayın:
    ```sh
    git clone https://github.com/miracbalkaya/simple-banking.git
    cd simple-banking
    ```

2. Gradle bağıml��lıklarını yükleyin:
    ```sh
    ./gradlew build
    ```
3. Docker build command:
    ```sh
    docker build -t simple-banking .
    ```
4. Docker run command:
    ```sh
   docker compose up
    ```


### Steps to Run Locally

1. Clone the repository:
   ```bash
   git clone https://github.com/your-username/simple-banking.git
