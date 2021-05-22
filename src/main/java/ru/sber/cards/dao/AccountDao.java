package ru.sber.cards.dao;

import ru.sber.cards.dao.models.Account;
import ru.sber.cards.dao.models.Transaction;
import ru.sber.cards.utilities.CreateUserException;

import java.util.List;

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
    /**
     * Достает из базы данных список всех переводов пользователей
     */
    List<Transaction> getTransactoinFromBD();
}
