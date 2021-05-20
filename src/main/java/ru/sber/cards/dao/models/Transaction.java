package ru.sber.cards.dao.models;

public class Transaction {

    private String idUserFrom;

    private String idUserTo;

    private int money;

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public String getIdUserFrom() {
        return idUserFrom;
    }

    public void setIdUserFrom(String idUserFrom) {
        this.idUserFrom = idUserFrom;
    }

    public String getIdUserTo() {
        return idUserTo;
    }

    public void setIdUserTo(String idUserTo) {
        this.idUserTo = idUserTo;
    }

}
