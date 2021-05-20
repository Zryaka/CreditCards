package ru.sber.cards.dao.models;

public class RegistrationRequest {
    private int id;

    private String name;

    private String lastName;

    private String password;

    private boolean loginOkey;

    public boolean isLoginOkey() {
        return loginOkey;
    }

    public void setLoginOkey(boolean loginOkey) {
        this.loginOkey = loginOkey;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
