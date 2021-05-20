package ru.sber.cards.dao.models;

public class AccountRequest {

    private int userIdRequest;

    private int balance;

    private int accountId;

    public int getUserIdRequest() {
        return userIdRequest;
    }

    public void setUserIdRequest(int userIdRequest) {
        this.userIdRequest = userIdRequest;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }
}
