package ru.sber.cards.dao;

import org.junit.Assert;
import org.junit.Test;
import ru.sber.cards.dao.models.LoginRequest;
import ru.sber.cards.dao.models.User;
import ru.sber.cards.utilities.CreateUserException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.Assert.*;


public class UserDaoImplTest {


    @Test
    public void saveNewUserNegative() {
        UserDaoImpl userDao = new UserDaoImpl();
        User user = new User();
        user.setName(null);
        user.setLastName("Petrov");
        user.setPassword("3123xa");
        Throwable exception = assertThrows(CreateUserException.class, () -> userDao.saveNewUser(user));
        assertEquals("Создание нового пользователя невозможно", exception.getMessage());
    }

    @Test
    public void saveNewUserPositive() {
        UserDaoImpl userDao = new UserDaoImpl();
        User user1 = new User();
        user1.setName("Tom");
        user1.setLastName("Cryz");
        user1.setPassword("28061994");
        userDao.saveNewUser(user1);

        User user2 = new User();
        String sqlRequest = "SELECT * FROM USER WHERE NAME = 'Tom' and LASTNAME = 'Cryz';";
        try (Connection connection = H2DataBase.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlRequest)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                user2.setName(resultSet.getString("NAME"));
                user2.setLastName(resultSet.getString("LASTNAME"));
                user2.setPassword(resultSet.getString("PASSWORD"));
            }
        } catch (SQLException e) {
            throw new CreateUserException("Пользователь не найден");
        }
        Assert.assertEquals(user1, user2);
    }

 /*  @Test
    public void loginChekNegative() {
        UserDaoImpl userDao = new UserDaoImpl();
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setName(null);
        loginRequest.setPassword("224ed1");
        Throwable exception = assertThrows(CreateUserException.class, () -> userDao.loginChek(loginRequest));
        assertEquals("Неверный логин или пароль", exception.getMessage());
    }*/

    @Test
    public void loginChekPositive() {
        UserDaoImpl userDao = new UserDaoImpl();
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setName("Tom");
        loginRequest.setPassword("28061994");

        String sqlRequest = "SELECT * FROM USER WHERE NAME = 'Tom' and PASSWORD = 28061994 ;";
        LoginRequest loginRequest1 = new LoginRequest();
        try (Connection connection = H2DataBase.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlRequest)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                loginRequest1.setName(resultSet.getString("Name"));
                loginRequest1.setPassword(resultSet.getString("Password"));
            }
        } catch (SQLException e) {
            throw new CreateUserException("Пользователь не найден");
        }
        Assert.assertEquals(loginRequest, loginRequest1);
    }

 /*   @Test
    public void setLoginTrueNegative() {
        UserDaoImpl userDao = new UserDaoImpl();
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setName("djjsdsdfsfdshjshcjshiudhcsdiuhcis");
        loginRequest.setPassword(null);
        userDao.setLoginTrue(loginRequest);
        Throwable exception = assertThrows(CreateUserException.class, () -> userDao.setLoginTrue(loginRequest));
        assertEquals("Ошибка при изменении состояния", exception.getMessage());
    }
*/
    @Test
    public void setLoginTruePositive() {
        UserDaoImpl userDao = new UserDaoImpl();
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setName("Tom");
        loginRequest.setPassword("28061994");
        userDao.setLoginTrue(loginRequest);

        String sqlRequest = "SELECT * FROM USER WHERE NAME = 'Tom' and CHECKLOGIN = True;";
        User user = new User();
        try (Connection connection = H2DataBase.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlRequest)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                user.setLoginOkey(resultSet.getBoolean("CHECKLOGIN"));
            }
        } catch (SQLException e) {
            throw new CreateUserException("Пользователь не найден");
        }
        Assert.assertTrue(user.isLoginOkey());
    }

    @Test
    public void checkUserLoginNegative() {
        UserDaoImpl userDao = new UserDaoImpl();
        Assert.assertEquals(false, userDao.checkUserLogin(0));

    }
    @Test
    public void checkUserLoginPositive() {
        UserDaoImpl userDao = new UserDaoImpl();
        Assert.assertEquals(true, userDao.checkUserLogin(1));
    }

    @Test
    public void getUserInfoNegative() {
        UserDaoImpl userDao = new UserDaoImpl();
        Assert.assertTrue(userDao.getUserInfo(0).isEmpty());
    }
    @Test
    public void getUserInfoPositive() {
        UserDaoImpl userDao = new UserDaoImpl();
        Assert.assertFalse(userDao.getUserInfo(1).isEmpty());
    }

    @Test
    public void getUserIdFromAccountNegative() {
        UserDaoImpl userDao = new UserDaoImpl();
        Assert.assertTrue(userDao.getUserIdFromAccount(0) == 0);
    }

    @Test
    public void getUserIdFromAccountPositive() {
        UserDaoImpl userDao = new UserDaoImpl();
        Assert.assertFalse(userDao.getUserIdFromAccount(1) == 0);
    }
}