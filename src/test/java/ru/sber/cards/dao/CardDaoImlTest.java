package ru.sber.cards.dao;

import org.junit.Assert;
import org.junit.Test;
import ru.sber.cards.dao.models.Card;
import ru.sber.cards.dao.models.CardResponse;
import ru.sber.cards.utilities.CreateUserException;
import ru.sber.cards.utilities.RandomCard;

import java.util.List;


public class CardDaoImlTest {

    @Test(expected = CreateUserException.class)
    public void chekAccountCreateNegative() {
        CardDao cardDao = new CardDaoIml();
        int idUser = 0;
        Assert.assertEquals(false,cardDao.chekAccountCreate(idUser));
    }

    @Test
    public void chekAccountCreatePositive() {
        CardDao cardDao = new CardDaoIml();
        int idUser = 1;
        Assert.assertEquals(true,cardDao.chekAccountCreate(idUser));
    }


    @Test(expected = CreateUserException.class)
    public void createNewCardNegative() {
        CardDao cardDao = new CardDaoIml();
        Card card = new Card();
        card.setNumber("scscs");
        card.setId(0);
        cardDao.createNewCard(card);
    }

    @Test
    public void createNewCardPositive() {
        CardDao cardDao = new CardDaoIml();
        Card card = new Card();
        card.setNumber(RandomCard.createNewCard());
        card.setUserId(1);
        cardDao.createNewCard(card);
    }

    @Test
    public void getListCardNegative() {
        CardDao cardDao = new CardDaoIml();
        List<CardResponse> cards = cardDao.getListCard(0);
        Assert.assertTrue(cards.isEmpty());
    }

    @Test
    public void getListCard() {
        CardDao cardDao = new CardDaoIml();
        List<CardResponse> cards = cardDao.getListCard(1);
        Assert.assertFalse(cards.isEmpty());
    }
}