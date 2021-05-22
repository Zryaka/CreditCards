package ru.sber.cards.dao.models;

import java.util.Objects;

public class Transaction {

    private int id;

    private int idUserFrom;

    private String Operation;

    private int money;

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdUserFrom() {
        return idUserFrom;
    }

    public void setIdUserFrom(int idUserFrom) {
        this.idUserFrom = idUserFrom;
    }

    public String getOperation() {
        return Operation;
    }

    public void setOperation(String operation) {
        Operation = operation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return id == that.id && idUserFrom == that.idUserFrom && money == that.money && Objects.equals(Operation, that.Operation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, idUserFrom, Operation, money);
    }
}
