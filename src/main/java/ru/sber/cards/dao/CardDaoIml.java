package ru.sber.cards.dao;

import ru.sber.cards.dao.models.Card;
import ru.sber.cards.dao.models.CardResponse;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CardDaoIml implements CardDao {

    private static final String CHEK_ACCOUNT = "SELECT SCORE FROM ACCOUNT WHERE ID_USER = ?;";
    private static final String CREATE_NEW_CARD = "INSERT INTO CARD(ID,NUMBER,ID_ACCOUNT) VALUES(default,?,?);";
    private static final String LINK_CARD = "SELECT ID, NUMBER FROM CARD WHERE ID_ACCOUNT = ?;";
    private List<CardResponse> cards = new ArrayList<>();

    @Override
    public boolean chekAccountCreate(int UserId) {
        try (Connection connection = H2DataBase.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(CHEK_ACCOUNT)) {
            preparedStatement.setInt(1, UserId);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            System.out.println("Для создания карты требуется завести счет");
        }
        return false;
    }

    @Override
    public void createNewCard(Card card) {
        try (Connection connection = H2DataBase.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(CREATE_NEW_CARD)) {
            preparedStatement.setString(1, card.getNumber());
            preparedStatement.setInt(2, card.getUserId());

            preparedStatement.execute();
        } catch (SQLException e) {
            System.out.println("Карта не создана!");
        }
    }

    @Override
    public List<CardResponse> getListCard(int accountId) {
        try (Connection connection = H2DataBase.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(LINK_CARD)) {
            preparedStatement.setInt(1, accountId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                CardResponse cardResponse = new CardResponse();
                cardResponse.setId(resultSet.getInt("ID"));
                cardResponse.setNumber(resultSet.getString("NUMBER"));

                cards.add(cardResponse);
            }
            return cards;
        } catch (SQLException e) {
            System.out.println("Неудается получить доступ к списку карт");
        }
        return cards;
    }


}
