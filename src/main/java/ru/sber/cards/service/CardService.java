package ru.sber.cards.service;

import ru.sber.cards.dao.models.Card;
import ru.sber.cards.dao.models.CardRequest;
import ru.sber.cards.dao.models.CardResponse;

import java.sql.SQLException;
import java.util.List;

public interface CardService {
    /**
     * Создание новое кредитной карты
     */
    void createCard(CardRequest cardRequest) throws SQLException;
    /**
     * Просмотр списка карт по определенному счету
     */
    List<CardResponse> linkCard(int accountId);

}
