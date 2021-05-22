package ru.sber.cards.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import ru.sber.cards.controlller.BaseServer;
import ru.sber.cards.dao.AccountDao;
import ru.sber.cards.dao.AccountDaoImpl;
import ru.sber.cards.dao.H2DataBase;
import ru.sber.cards.dao.models.Account;
import ru.sber.cards.dao.models.Transaction;
import ru.sber.cards.utilities.CreateUserException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AccountServiceImplTest {

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
    public void createAccount() throws IOException {
        final URL url = new URL("http://localhost:8080/account/create");
        final HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json; utf-8");
        con.setRequestProperty("Accept", "application/json");

        String jsonString = "{\"userIdRequest\" : \"45\"}";

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
        String sqlString = "SELECT SCORE FROM ACCOUNT WHERE ID_USER = 45;";

        Account account = new Account();
        try (Connection connection = H2DataBase.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlString)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                account.setScore(resultSet.getString("score"));
            }
        } catch (SQLException e) {
            throw new CreateUserException("Счет отсутствует");
        }
        Assertions.assertNotNull(account.getScore());

    }

    @Test
    public void addMoney() throws IOException {
        AccountDao accountDao = new AccountDaoImpl();
        final URL url = new URL("http://localhost:8080/account/add");
        final HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json; utf-8");
        con.setRequestProperty("Accept", "application/json");

        String jsonString = "{\"accountId\": \"3\",\"balance\":\"400\"}";
        int balanceBefore = accountDao.getUserBalance(3);
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
        int balanceAfter = accountDao.getUserBalance(3);
        Assert.assertEquals(balanceBefore, balanceAfter - 400);
    }

    @Test
    public void takeMoney() throws IOException {
        AccountDao accountDao = new AccountDaoImpl();
        final URL url = new URL("http://localhost:8080/account/withdraw");
        final HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json; utf-8");
        con.setRequestProperty("Accept", "application/json");

        String jsonString = "{\"accountId\": \"3\",\"balance\":\"400\"}";
        int balanceBefore = accountDao.getUserBalance(3);

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
        int balanceAfter = accountDao.getUserBalance(3);
        Assert.assertEquals(balanceBefore, balanceAfter + 400);

    }

    @Test
    public void linkBalance() throws IOException {
        AccountDao accountDao = new AccountDaoImpl();
        final URL url = new URL("http://localhost:8080/account/balance?accountId=1");
        final HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("Content-Type", "application/json");
        String inp = "";
        try (final BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
            String inputLine;
            final StringBuilder content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            inp = content.toString();
        } catch (final Exception ex) {
            ex.printStackTrace();
        }
        int balance = Integer.parseInt(inp);
        int balance2 = accountDao.getUserBalance(1);

        Assert.assertEquals(balance, balance2);
    }


    @Test
    public void transactionToAccount() throws IOException {
        AccountDao accountDao = new AccountDaoImpl();
        final URL url = new URL("http://localhost:8080/account/transaction");
        final HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json; utf-8");
        con.setRequestProperty("Accept", "application/json");

        List<Transaction> transactions = accountDao.getTransactoinFromBD();
        String jsonString = "{accountIdUser1\": \"3\",\"accountNumberUser2\":\"37292599907170658484\",\"sumMoney\":\"500\"}";
        con.setDoOutput(true);
        try (OutputStream os = con.getOutputStream()) {
            byte[] input = jsonString.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }
        String sqlString = "SELECT * FROM COUNTERPARTY";
        List<Transaction> transactions1 = new ArrayList<>();
        try (Connection connection = H2DataBase.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlString)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Transaction transaction = new Transaction();
                transaction.setId(resultSet.getInt("ID"));
                transaction.setIdUserFrom(resultSet.getInt("ID_USER_FROM"));
                transaction.setOperation(resultSet.getString("OPERATION"));
                transaction.setMoney(resultSet.getInt("SUM"));

                transactions1.add(transaction);
            }
        } catch (SQLException e) {
            throw new CreateUserException("Счет отсутствует");
        }
        Assert.assertFalse(transactions.contains(transactions1));
    }

    @Test
    public void linkTransaction() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        AccountDao accountDao = new AccountDaoImpl();
        List<Transaction> transactions = new ArrayList<>();
        List<Transaction> transactions1 = accountDao.getTransactoinFromBD();
        final URL url = new URL("http://localhost:8080/transaction");
        final HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("Content-Type", "application/json");
        String operation = "";
        try (final BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
            String inputLine;
            final StringBuilder content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            operation = content.toString();

        } catch (final Exception ex) {
            ex.printStackTrace();
        }
        transactions = Arrays.asList(mapper.readValue(operation, Transaction[].class));

        Assertions.assertEquals(transactions, transactions1);


    }
}
