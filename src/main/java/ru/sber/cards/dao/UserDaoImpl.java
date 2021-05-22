package ru.sber.cards.dao;

import ru.sber.cards.dao.models.LoginRequest;
import ru.sber.cards.dao.models.User;
import ru.sber.cards.utilities.CreateUserException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDaoImpl implements UserDao {

    private static final String INSERT_USER_SQL = "INSERT INTO USER" +
            "  (ID, NAME , LASTNAME, PASSWORD) VALUES " +
            " (default , ?, ?, ?);";
    private static final String CHEK_LOGIN = "SELECT * FROM USER WHERE NAME =?;";
    private static final String SET_TRUE = "UPDATE USER SET CHECKLOGIN = true WHERE NAME = ? AND PASSWORD = ?;";
    private static final String CHEK_USER = "SELECT * FROM USER WHERE ID = ? AND CHECKLOGIN = TRUE;";
    private static final String USER_INFO = "SELECT C.ID ,NAME, LASTNAME FROM USER C " +
            " JOIN  ACCOUNT A on C.ID = A.ID_USER" +
            " WHERE A.ID = ?;";
    private static final String GET_USER_ID ="SELECT C.ID FROM USER C JOIN ACCOUNT A ON C.ID = A.ID_USER WHERE A.ID = ?;";

    @Override
    public void saveNewUser(User user){
        try (Connection connection = H2DataBase.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USER_SQL)) {
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setString(3, user.getPassword());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new CreateUserException("Создание нового пользователя невозможно");

        }
    }

    @Override
    public boolean loginChek(LoginRequest loginRequest)throws CreateUserException {
        try (Connection connection = H2DataBase.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(CHEK_LOGIN)) {
            preparedStatement.setString(1, loginRequest.getName());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String password = resultSet.getString("password");
                if (password.equals(loginRequest.getPassword())) {
                    return true;
                } else {
                    return false;
                }
            }
        } catch (SQLException e) {
            throw new CreateUserException("Неверный логин или пароль");
        }
        return true;
    }

    @Override
    public void setLoginTrue(LoginRequest loginRequest) {
        try (Connection connection = H2DataBase.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SET_TRUE)) {
                 preparedStatement.setString(1, loginRequest.getName());
                 preparedStatement.setString(2, loginRequest.getPassword());

                 preparedStatement.execute();
        } catch (SQLException e) {
            throw new CreateUserException("Ошибка при изменении состояния");
        }
    }

    @Override
    public boolean checkUserLogin(int id) {
        try (Connection connection = H2DataBase.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(CHEK_USER)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            throw new CreateUserException("Пользователь не авторизован!");
        }

    }

    @Override
    public String getUserInfo(int accountId) {
        String info = "";
        try (Connection connection = H2DataBase.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(USER_INFO)) {
            preparedStatement.setInt(1, accountId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt("ID"));
                user.setName(resultSet.getString("NAME"));
                user.setLastName(resultSet.getString("LASTNAME"));

                info = user.toString();
            }
        } catch (SQLException e) {
            System.out.println("Неудалось получить данные пользователя");
        }
        return info;
    }

    @Override
    public int getUserIdFromAccount(int idAccount) {
        int idUser = 0;
        try (Connection connection = H2DataBase.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_USER_ID)) {
            preparedStatement.setInt(1, idAccount);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
               idUser = resultSet.getInt("ID");
            }
            return idUser;
        } catch (SQLException e) {
            System.out.println("Неудалось получить данные пользователя");
        }
        return idUser;
    }

}
