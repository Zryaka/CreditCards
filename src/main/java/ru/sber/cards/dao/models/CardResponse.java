package ru.sber.cards.dao.models;

public class CardResponse {
    private int id;

    private String number;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    @Override
    public String toString() {
        return "CardResponse{" +
                "id=" + id +
                ", number='" + number + '\'' +
                '}';

    }
}
