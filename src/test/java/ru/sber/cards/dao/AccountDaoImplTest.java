package ru.sber.cards.dao;


import org.junit.Assert;
import org.junit.Test;
import ru.sber.cards.dao.models.Account;
import ru.sber.cards.dao.models.Transaction;
import ru.sber.cards.utilities.CreateUserException;

import java.util.List;

public class AccountDaoImplTest{

    @Test(expected = CreateUserException.class)
    public void saveAccount() {
        AccountDao accountDao = new AccountDaoImpl();
        Account account = new Account();
        account.setScore(null);
        account.setUserID(235125);
        account.setBalance(0);
        accountDao.saveAccount(account);
    }

    @Test(expected = CreateUserException.class)
    public void getUserBalance() {
        AccountDao accountDao = new AccountDaoImpl();
        int id = 0;
        accountDao.getUserBalance(0);
    }

    @Test(expected = CreateUserException.class)
    public void changeMoneyInAccount() {
        AccountDao accountDao = new AccountDaoImpl();
        Account account = new Account();
        account.setBalance(234523);
        account.setUserID(0);
        accountDao.changeMoneyInAccount(account);
    }

    @Test(expected = CreateUserException.class)
    public void takeBalanceAccount() {
        AccountDao accountDao = new AccountDaoImpl();
        String score = "211441";
        Assert.assertEquals(accountDao.takeBalanceAccount(score),0);
    }

    @Test(expected = CreateUserException.class)
    public void takeIdAccount() {
        AccountDao accountDao = new AccountDaoImpl();
        String score = "2141236";
        accountDao.takeIdAccount(score);

    }

    @Test(expected = CreateUserException.class)
    public void saveTransaction() {
        AccountDao accountDao = new AccountDaoImpl();
        Transaction transaction = new Transaction();
        transaction.setIdUserFrom(3);
        transaction.setOperation(null);
        transaction.setMoney(23);
        accountDao.saveTransaction(transaction);
    }

    @Test
    public void getTransactoinFromBD() {
        AccountDao accountDao = new AccountDaoImpl();
        List<Transaction> transactions = accountDao.getTransactoinFromBD();
        Assert.assertFalse(transactions.isEmpty());
    }
}