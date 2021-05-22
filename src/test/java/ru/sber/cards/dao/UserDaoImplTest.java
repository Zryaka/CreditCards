package ru.sber.cards.dao;


import org.junit.Assert;
import org.junit.Test;
import ru.sber.cards.dao.models.LoginRequest;
import ru.sber.cards.dao.models.User;
import ru.sber.cards.utilities.CreateUserException;

import static org.junit.Assert.*;


public class UserDaoImplTest {


    @Test
    public void saveNewUser() {
        UserDaoImpl userDao = new UserDaoImpl();
        User user = new User();
        user.setName(null);
        user.setLastName("Petrov");
        user.setPassword("3123xa");
        Throwable exception = assertThrows(CreateUserException.class, () -> userDao.saveNewUser(user));
        assertEquals("Создание нового пользователя невозможно", exception.getMessage());
    }

    @Test
    public void loginChek() {
        UserDaoImpl userDao = new UserDaoImpl();
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setName("al");
        loginRequest.setPassword("224ed1");
        Throwable exception = assertThrows(CreateUserException.class, () -> userDao.loginChek(loginRequest));
        assertEquals("Неверный логин или пароль", exception.getMessage());
    }

    @Test(expected = CreateUserException.class)
    public void setLoginTrue() {
        UserDaoImpl userDao = new UserDaoImpl();
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setName("djjsdsdfsfdshjshcjshiudhcsdiuhcis");
        loginRequest.setPassword("ss906898d069u3904uf9");
        userDao.setLoginTrue(loginRequest);

    }

    @Test
    public void checkUserLogin() {
        UserDaoImpl userDao = new UserDaoImpl();
        int id = 0;
        Assert.assertEquals(false,userDao.checkUserLogin(0));

    }

    @Test
    public void getUserInfo() {
        UserDaoImpl userDao = new UserDaoImpl();
        int id = 0;
        Assert.assertTrue(userDao.getUserInfo(0).isEmpty());

    }

    @Test
    public void getUserIdFromAccount() {
        UserDaoImpl userDao = new UserDaoImpl();
        int id = 0;
        Assert.assertTrue(userDao.getUserIdFromAccount(0) == 0);
    }
}