package ru.sber.cards.dao;

import ru.sber.cards.dao.models.Account;
import ru.sber.cards.dao.models.Transaction;

import java.sql.SQLException;

public interface AccountDao {
     /**
      * Сохранение нового банковского счета в базу данных
      */
    void saveAccount(Account account);
    /**
     * Запрос балнса пользователя
     */
    int getUserBalance(int id);
    /**
     * Изменение баланса на счете
     */
    void changeMoneyInAccount(Account account);
    /**
     * Добавление денег на счет, через номер счета
     */
    int takeBalanceAccount(String score);
    /**
     * Получение ид счета по номеру счета
     */
    int takeIdAccount(String score);
    /**
     * Сохранение операций перевода средств в бд
     */
    void saveTransaction(Transaction transaction);
}
