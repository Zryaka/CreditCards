package ru.sber.cards;

import ru.sber.cards.controlller.BaseServer;

import java.io.IOException;
import java.sql.SQLException;


public class StartServer {
    public static void main(String[] args) throws SQLException {
     try {
            new BaseServer().startServer();
            System.out.println("Сервер запущен");
        } catch (IOException e) {
            System.out.println("Сервер не запущен");
        }

    }
}
