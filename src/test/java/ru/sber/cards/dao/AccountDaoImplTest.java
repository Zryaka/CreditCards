package ru.sber.cards.dao;


import org.junit.Assert;
import org.junit.Test;
import ru.sber.cards.dao.models.Account;
import ru.sber.cards.dao.models.Transaction;
import ru.sber.cards.dao.models.User;
import ru.sber.cards.utilities.CreateUserException;
import ru.sber.cards.utilities.RandomAccount;
import sun.management.counter.AbstractCounter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class AccountDaoImplTest{

    @Test(expected = CreateUserException.class)
    public void saveAccountNegative() {
        AccountDao accountDao = new AccountDaoImpl();
        Account account = new Account();
        account.setScore(null);
        account.setUserID(235125);
        account.setBalance(0);
        accountDao.saveAccount(account);
    }

    @Test
    public void saveAccountPositive() {
        AccountDao accountDao = new AccountDaoImpl();
        Account account = new Account();
        account.setScore(RandomAccount.createNewAcoount());
        account.setUserID(19);
        account.setBalance(0);
        accountDao.saveAccount(account);

    String sqlString = "SELECT * FROM ACCOUNT WHERE ID_USER = 45 AND BALANCE = 0;";
        Account account1 = new Account();
        try (Connection connection = H2DataBase.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlString)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
             account.setBalance(resultSet.getInt("BALANCE"));
             account.setUserID(resultSet.getInt("ID_USER"));
             account.setScore(resultSet.getString("SCORE"));
            }
        } catch (SQLException e) {
            throw new CreateUserException("Пользователь не найден");
        }
        Assert.assertFalse(account.getScore().isEmpty());
    }

    @Test(expected = CreateUserException.class)
    public void getUserBalanceNegative() {
        AccountDao accountDao = new AccountDaoImpl();
        accountDao.getUserBalance(0);
    }

    @Test
    public void getUserBalancePositive() {
        AccountDao accountDao = new AccountDaoImpl();
        int balance = accountDao.getUserBalance(1);
        int balance2 = 0;
        String sqlString = "SELECT BALANCE FROM ACCOUNT WHERE ID = 1;";
        try (Connection connection = H2DataBase.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlString)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                balance2 = resultSet.getInt("balance");
            }
        } catch (SQLException e) {
            throw new CreateUserException("Счет не найден");
        }
        Assert.assertEquals(balance,balance2);
    }

   /* @Test(expected = CreateUserException.class)
    public void changeMoneyInAccountNegative() {
        AccountDao accountDao = new AccountDaoImpl();
        Account account = new Account();
        account.setBalance(234523);
        account.setUserID(0);
        accountDao.changeMoneyInAccount(account);
    }*/

    @Test
    public void changeMoneyInAccountPositive() {
        AccountDao accountDao = new AccountDaoImpl();
        int balanceBefore = accountDao.getUserBalance(1);
        Account account = new Account();
        account.setBalance(balanceBefore + 300);
        account.setId(1);
        accountDao.changeMoneyInAccount(account);
         int balanceAfter = 0;
        String sqlString = "SELECT BALANCE FROM ACCOUNT WHERE ID = 1;";
        try (Connection connection = H2DataBase.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlString)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                 balanceAfter = resultSet.getInt("balance");
            }
        } catch (SQLException e) {
            throw new CreateUserException("Счет не найден");
        }

        Assert.assertEquals(balanceBefore + 300,balanceAfter);
    }

    @Test(expected = CreateUserException.class)
    public void takeBalanceAccountNegative() {
        AccountDao accountDao = new AccountDaoImpl();
        String score = "211441";
        Assert.assertEquals(accountDao.takeBalanceAccount(score),32324);
    }

    @Test
    public void takeBalanceAccountPositive() {
        AccountDao accountDao = new AccountDaoImpl();
        String score = "71381552793559178688";
        Assert.assertTrue(accountDao.takeBalanceAccount(score) != 0);
    }

    @Test(expected = CreateUserException.class)
    public void takeIdAccountNegative() {
        AccountDao accountDao = new AccountDaoImpl();
        String score = "2141236";
        accountDao.takeIdAccount(score);
    }

    @Test
    public void takeIdAccountPositive() {
        AccountDao accountDao = new AccountDaoImpl();
        String score ="37292599907170658484";
        Assert.assertEquals(accountDao.takeIdAccount(score), 2);
    }

    @Test(expected = CreateUserException.class)
    public void saveTransactionNegative() {
        AccountDao accountDao = new AccountDaoImpl();
        Transaction transaction = new Transaction();
        transaction.setIdUserFrom(3);
        transaction.setOperation(null);
        transaction.setMoney(23);
        accountDao.saveTransaction(transaction);
    }

    @Test
    public void saveTransactionPositive() {
        AccountDao accountDao = new AccountDaoImpl();
        Transaction transaction = new Transaction();
        transaction.setIdUserFrom(3);
        transaction.setOperation("Add money");
        transaction.setMoney(250);
        accountDao.saveTransaction(transaction);
        Transaction  transaction1 = new Transaction();
        String sqlString = "SELECT * FROM COUNTERPARTY WHERE ID_USER_FROM = 3 AND SUM = 250";
        try (Connection connection = H2DataBase.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlString)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                transaction1.setIdUserFrom(resultSet.getInt("ID_USER_FROM"));
                transaction1.setOperation(resultSet.getString("OPERATION"));
                transaction1.setMoney(resultSet.getInt("SUM"));
            }
        } catch (SQLException e) {
            throw new CreateUserException("Счет не найден");
        }
        Assert.assertEquals(transaction,transaction1);
    }

    @Test
    public void getTransactoinFromBD() {
        AccountDao accountDao = new AccountDaoImpl();
        List<Transaction> transactions = accountDao.getTransactoinFromBD();
        Assert.assertFalse(transactions.isEmpty());
    }

}