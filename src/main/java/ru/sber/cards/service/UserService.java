package ru.sber.cards.service;


import ru.sber.cards.dao.models.LoginRequest;
import ru.sber.cards.dao.models.RegistrationRequest;

import java.sql.SQLException;

public interface UserService {
    /**
     * Регистрация нового пользователя
     */
    void registration(RegistrationRequest registrationRequest) throws SQLException;
    /**
     * Авторизация нового пользователя в системе*/
    void login(LoginRequest loginRequest) throws Exception;
}
