package ru.sber.cards.dao;

import org.junit.Assert;
import org.junit.Test;
import ru.sber.cards.dao.models.Card;
import ru.sber.cards.dao.models.CardResponse;
import ru.sber.cards.utilities.CreateUserException;

import java.util.List;


public class CardDaoImlTest {

    @Test(expected = CreateUserException.class)
    public void chekAccountCreate() {
        CardDao cardDao = new CardDaoIml();
        int idUser = 0;
        Assert.assertEquals(false,cardDao.chekAccountCreate(idUser));
    }

    @Test(expected = CreateUserException.class)
    public void createNewCard() {
        CardDao cardDao = new CardDaoIml();
        Card card = new Card();
        card.setNumber("scscs");
        card.setId(0);
        cardDao.createNewCard(card);

    }

    @Test
    public void getListCard() {
        CardDao cardDao = new CardDaoIml();
        List<CardResponse> cards = cardDao.getListCard(0);
        Assert.assertTrue(cards.isEmpty());
    }
}