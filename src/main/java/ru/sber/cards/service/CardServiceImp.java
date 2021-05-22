package ru.sber.cards.service;

import ru.sber.cards.dao.CardDao;
import ru.sber.cards.dao.models.Card;
import ru.sber.cards.dao.models.CardRequest;
import ru.sber.cards.dao.models.CardResponse;
import ru.sber.cards.utilities.RandomCard;

import java.sql.SQLException;
import java.util.List;

public class CardServiceImp implements CardService{

    private final CardDao cardDao;
    public CardServiceImp(CardDao cardDao) {
        this.cardDao = cardDao;
    }
    private final int balance = 0;

    @Override
    public void createCard(CardRequest cardRequest) throws SQLException {
        if(cardDao.chekAccountCreate(cardRequest.getIdUser())) {
            Card card = new Card();
            card.setNumber(RandomCard.createNewCard());
            card.setUserId(cardRequest.getIdUser());
            cardDao.createNewCard(card);
        }else {
            throw new SQLException("Счет не найден");
        }
    }

    @Override
    public List<CardResponse> linkCard(int accountId) {
        List<CardResponse> cards = cardDao.getListCard(accountId);
        return cards;
    }
}
