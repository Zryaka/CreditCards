package ru.sber.cards.service;

import ru.sber.cards.dao.CardDao;
import ru.sber.cards.dao.models.Card;
import ru.sber.cards.dao.models.CardRequest;
import ru.sber.cards.dao.models.CardResponse;
import ru.sber.cards.utilities.CreateUserException;
import ru.sber.cards.utilities.RandomCard;

import java.util.List;

public class CardServiceImp implements CardService{

    private final CardDao cardDao;
    public CardServiceImp(CardDao cardDao) {
        this.cardDao = cardDao;
    }

    @Override
    public void createCard(CardRequest cardRequest){
        if(cardDao.chekAccountCreate(cardRequest.getIdUser())) {
            Card card = new Card();
            card.setNumber(RandomCard.createNewCard());
            card.setUserId(cardRequest.getIdUser());
            cardDao.createNewCard(card);
        }else {
            throw new CreateUserException("Счет не найден");
        }
    }

    @Override
    public List<CardResponse> linkCard(int accountId) {
        List<CardResponse> cards = cardDao.getListCard(accountId);
        return cards;
    }
}
