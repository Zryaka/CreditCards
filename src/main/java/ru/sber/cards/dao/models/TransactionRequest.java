package ru.sber.cards.dao.models;

public class TransactionRequest {

    private int accountIdUser1;

    private String accountNumberUser2;

    private int sumMoney;

    public int getAccountIdUser1() {
        return accountIdUser1;
    }

    public void setAccountIdUser1(int accountIdUser1) {
        this.accountIdUser1 = accountIdUser1;
    }

    public String getAccountNumberUser2() {
        return accountNumberUser2;
    }

    public void setAccountNumberUser2(String accountNumberUser2) {
        this.accountNumberUser2 = accountNumberUser2;
    }

    public int getSumMoney() {
        return sumMoney;
    }

    public void setSumMoney(int sumMoney) {
        this.sumMoney = sumMoney;
    }
}
