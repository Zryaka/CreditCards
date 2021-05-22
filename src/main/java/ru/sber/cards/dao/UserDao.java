package ru.sber.cards.dao;

import ru.sber.cards.dao.models.LoginRequest;
import ru.sber.cards.dao.models.User;
import ru.sber.cards.utilities.CreateUserException;

import java.sql.SQLException;

public interface UserDao {
    /**
     * Сохранение нового пользователя в БД
     */
    void saveNewUser(User User);
    /**
     * Проверка пароля
     */
     boolean loginChek(LoginRequest loginRequest) throws CreateUserException;
     /**
      * Если проверка прошла удачно то переставляем переменную в БД на True
      * Пользователь залогинен
      */
     void setLoginTrue(LoginRequest loginRequest);
     /**
      * Проверка что пользователь залогинен
      * если по проверке логина возвращается true, то пользователь может совершать дальнейшие операции
      */
     boolean checkUserLogin(int id);
      /**
       * Метод: достает пользователя из базы данных по номеру счета
       * возвращает объект в виде строки
       */
      String getUserInfo(int accountId);
      /**
       * Метод: который возвращает ID пользователя по ID счета
       */
      int getUserIdFromAccount(int idAccount);

}
