package ru.sber.cards.dao.models;

public class Account {

    private int Id;

    private String score;

    private int userID;

    private int balance;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }
}