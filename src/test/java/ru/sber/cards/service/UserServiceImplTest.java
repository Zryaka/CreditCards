package ru.sber.cards.service;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import ru.sber.cards.controlller.BaseServer;
import ru.sber.cards.dao.H2DataBase;
import ru.sber.cards.dao.models.User;
import ru.sber.cards.utilities.CreateUserException;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class UserServiceImplTest {
    @BeforeAll
    public static void init() {
        try {
            new BaseServer().startServer();
            System.out.println("Сервер запущен");
        } catch (IOException e) {
            System.out.println("Сервер не запущен");
        }
    }

    @Test
    public void registration() throws IOException {

        final URL url = new URL("http://localhost:8080/register");
        final HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json; utf-8");
        con.setRequestProperty("Accept", "application/json");

        String jsonString = "{\"name\": \"Alexey\",\"lastName\": \"Galitsyn\",\"password\" : \"20212021\"}";
        User user1 = new User();
        user1.setName("Alexey");
        user1.setLastName("Galitsyn");

        con.setDoOutput(true);
        try (OutputStream os = con.getOutputStream()) {
            byte[] input = jsonString.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8))) {
            StringBuilder response = new StringBuilder();
            String responseLine = null;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
            System.out.println(response.toString());
        }

        String sqlGetUser = "SELECT * FROM USER WHERE NAME = 'Alexey' AND LASTNAME = 'Galitsyn';";

        User user = new User();
        try (Connection connection = H2DataBase.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlGetUser)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastname"));
            }
        } catch (SQLException e) {
            throw new CreateUserException("Неверный логин или пароль");
        }

        Assert.assertEquals(user, user1);
    }

    @Test
    public void login() throws IOException {
        final URL url = new URL("http://localhost:8080/login");
        final HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json; utf-8");
        con.setRequestProperty("Accept", "application/json");

        String jsonString = "{\"name\": \"Alexey\",\"password\" : \"20212021\"}";
        User user1 = new User();
        user1.setName("Alexey");
        user1.setLoginOkey(true);

        con.setDoOutput(true);
        try (OutputStream os = con.getOutputStream()) {
            byte[] input = jsonString.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8))) {
            StringBuilder response = new StringBuilder();
            String responseLine = null;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
            System.out.println(response.toString());
        }

        String sqlGetUser = "SELECT * FROM USER WHERE NAME = 'Alexey';";

        User user = new User();
        try (Connection connection = H2DataBase.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlGetUser)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                user.setName(resultSet.getString("name"));
                user.setLoginOkey(resultSet.getBoolean("checklogin"));
            }
        } catch (SQLException e) {
            throw new CreateUserException("Неверный логин или пароль");
        }
        Assert.assertEquals(user, user1);
    }
}