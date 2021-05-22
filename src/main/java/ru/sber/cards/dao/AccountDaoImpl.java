package ru.sber.cards.dao;

import ru.sber.cards.dao.models.Account;
import ru.sber.cards.dao.models.Transaction;
import ru.sber.cards.utilities.CreateUserException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AccountDaoImpl implements AccountDao {

    private static final String SAVE_ACCOUNT = "INSERT INTO ACCOUNT(ID,SCORE,ID_USER,BALANCE)VALUES(default ,?,?,?);";
    private static final String GET_BALANCE = "SELECT BALANCE FROM ACCOUNT WHERE ID = ?;";
    private static final String CHANGE_BALANCE = "UPDATE ACCOUNT SET BALANCE = ? WHERE ID = ?;";
    private static final String GET_BALANCE_ACCOUNT = "SELECT BALANCE FROM ACCOUNT WHERE SCORE = ?;";
    private static final String GET_ID_ACCOUNT = "SELECT ID FROM ACCOUNT WHERE SCORE = ?;";
    private static final String SAVE_TRANSACTION = "INSERT INTO COUNTERPARTY(ID,ID_USER_FROM,OPERATION,SUM) VALUES (default,?,?,?);";
    private static final String LINC_TRANSACTION = "SELECT * FROM COUNTERPARTY;";
    @Override
    public void saveAccount(Account account) {
        try (Connection connection = H2DataBase.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SAVE_ACCOUNT)) {
            preparedStatement.setString(1, account.getScore());
            preparedStatement.setInt(2, account.getUserID());
            preparedStatement.setInt(3, account.getBalance());

            preparedStatement.execute();
        } catch (SQLException e) {
            throw new CreateUserException("Ошибка создания нового счета");
        }

    }

    @Override
    public int getUserBalance(int id) {
        int balance = 0;
        try (Connection connection = H2DataBase.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_BALANCE)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();

             balance = resultSet.getInt("balance");
        } catch (SQLException e) {
            throw new CreateUserException("Нет доступа к балансу счета");
        }
        return balance;
    }

    @Override
    public void changeMoneyInAccount(Account account) {
        try (Connection connection = H2DataBase.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(CHANGE_BALANCE)) {
            preparedStatement.setInt(1, account.getBalance());
            preparedStatement.setInt(2, account.getId());

            preparedStatement.execute();
        } catch (SQLException e) {
            throw new CreateUserException("Денежные средства не зачислены");
        }
    }

    @Override
    public int takeBalanceAccount(String score) {
        int balance = 0;
        try (Connection connection = H2DataBase.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_BALANCE_ACCOUNT)) {
            preparedStatement.setString(1,score);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();

            balance = resultSet.getInt("BALANCE");
        } catch (SQLException e) {
            throw new CreateUserException("Нет доступа к счету");
        }
        return balance;
    }

    @Override
    public int takeIdAccount(String score) {
        int idAccount = 0;
        try (Connection connection = H2DataBase.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_ID_ACCOUNT)) {
            preparedStatement.setString(1,score);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();

            idAccount = resultSet.getInt("ID");
        } catch (SQLException e) {
            throw new CreateUserException("Неудалось получить ID счета");
        }
        return idAccount;
    }

    @Override
    public void saveTransaction(Transaction transaction) {
        try (Connection connection = H2DataBase.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SAVE_TRANSACTION)) {
            preparedStatement.setInt(1, transaction.getIdUserFrom());
            preparedStatement.setString(2, transaction.getOperation());
            preparedStatement.setInt(3, transaction.getMoney());

            preparedStatement.execute();
        } catch (SQLException e) {
            throw new CreateUserException("Ошибка создания нового счета");
        }
    }

    @Override
    public List<Transaction> getTransactoinFromBD() {
        List<Transaction> transactions = new ArrayList<>();
        try (Connection connection = H2DataBase.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(LINC_TRANSACTION)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Transaction transaction = new Transaction();
                transaction.setId(resultSet.getInt("ID"));
                transaction.setIdUserFrom(resultSet.getInt("ID_USER_FROM"));
                transaction.setOperation(resultSet.getString("OPERATION"));
                transaction.setMoney(resultSet.getInt("SUM"));

                transactions.add(transaction);
            }
        } catch (SQLException e) {
            System.out.println("Неудается получить доступ к списку карт");
        }
        return transactions;
    }


}
