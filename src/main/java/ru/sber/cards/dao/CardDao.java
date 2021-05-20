package ru.sber.cards.dao;

import ru.sber.cards.dao.models.Card;

import ru.sber.cards.dao.models.CardResponse;

import java.util.List;

public interface CardDao {
    /**
     * Проверка, создан ли счет, перед созданием карты
     * если счета нет вернется False
     */
    boolean chekAccountCreate(int UserID);
    /**
     * Создание новой карты
     */
    void createNewCard(Card card);
    /**
     * Получение списка карт
     */
    List<CardResponse> getListCard(int accountId);
}
