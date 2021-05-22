package ru.sber.cards.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.sber.cards.controlller.BaseServer;
import ru.sber.cards.dao.*;
import ru.sber.cards.dao.models.CardResponse;
import ru.sber.cards.utilities.CreateUserException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CardServiceImpTest{

    @BeforeAll
    public static void init(){
        try {
            new BaseServer().startServer();
            System.out.println("Сервер запущен");
        } catch (IOException e) {
            System.out.println("Сервер не запущен");
        }

    }
    @Test

    public void createCard() throws IOException {
        CardDao cardDao = new CardDaoIml();
        CardService cardService = new CardServiceImp(cardDao);
        final URL url = new URL("http://localhost:8080/card");
        final HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json; utf-8");
        con.setRequestProperty("Accept", "application/json");

        List<CardResponse> cardResponses = cardService.linkCard(2);
        String jsonString = "{\"idUser\": \"2\"}";
        con.setDoOutput(true);
        try (OutputStream os = con.getOutputStream()) {
            byte[] input = jsonString.getBytes("utf-8");
            os.write(input, 0, input.length);
        }
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(con.getInputStream(), "utf-8"))) {
            StringBuilder response = new StringBuilder();
            String responseLine = null;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
            System.out.println(response.toString());
        }
        String sqlString = "SELECT * FROM CARD WHERE ID_ACCOUNT = 2;";
        List<CardResponse> cardResponses1 = new ArrayList<>();
        try (Connection connection = H2DataBase.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlString)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
              CardResponse cardResponse = new CardResponse();
              cardResponse.setId(resultSet.getInt("ID"));
              cardResponse.setNumber(resultSet.getString("number"));

              cardResponses1.add(cardResponse);
            }
        } catch (SQLException e) {
            throw new CreateUserException("Карты не найдены");
        }
        Assertions.assertFalse(cardResponses.contains(cardResponses1));
    }
    @Test
    public void linkCard() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        CardDao cardDao = new CardDaoIml();
        List<CardResponse> cards = new ArrayList<>();
        List<CardResponse> cards1 = cardDao.getListCard(1);
        final URL url = new URL("http://localhost:8080/card?accountId=1");
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
        cards = Arrays.asList(mapper.readValue(operation, CardResponse[].class));

        Assertions.assertEquals(cards, cards1);
    }
}