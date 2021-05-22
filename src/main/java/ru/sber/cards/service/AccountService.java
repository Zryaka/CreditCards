package ru.sber.cards.service;

import ru.sber.cards.dao.models.AccountRequest;
import ru.sber.cards.dao.models.Transaction;
import ru.sber.cards.dao.models.TransactionRequest;

import java.util.List;

public interface AccountService {
     /**
      * Создание нового счета для залогиненого пользователя
      */
    void createAccount(AccountRequest accountRequest);
    /**
    *Добавление денежных средст*/
    void addMoney(AccountRequest accountRequest);
    /**
     * Снять деньги со счета
     */
    void takeMoney(AccountRequest accountRequest);
    /**
     * Получение баланса счета
     */
    int linkBalance(int accountId);
    /**
     * Перевод денег на другой счет
     */
    void transactionToAccount(TransactionRequest transactionRequest);
    /**
     *Показывает все переводы совершенные пользователями
     */
    List<Transaction> linkTransaction();
}
