package ru.sber.cards.service;

import ru.sber.cards.dao.AccountDao;
import ru.sber.cards.dao.UserDao;
import ru.sber.cards.dao.models.Account;
import ru.sber.cards.dao.models.AccountRequest;
import ru.sber.cards.dao.models.Transaction;
import ru.sber.cards.dao.models.TransactionRequest;
import ru.sber.cards.utilities.RandomAccount;

import java.sql.SQLException;

public class AccountServiceImpl implements AccountService {

    private final UserDao userDao;
    private final AccountDao accountDao;

    public AccountServiceImpl(UserDao userDao, AccountDao accountDao) {
        this.userDao = userDao;
        this.accountDao = accountDao;
    }

    private int balance = 0;

    @Override
    public void createAccount(AccountRequest accountRequest) {
        if (userDao.checkUserLogin(accountRequest.getUserIdRequest())) {
            Account account = new Account();
            account.setScore(RandomAccount.createNewAcoount());
            account.setUserID(accountRequest.getUserIdRequest());
            account.setBalance(balance);
            accountDao.saveAccount(account);
        }
    }

    @Override
    public void addMoney(AccountRequest accountRequest) {
        Account account = new Account();
        account.setId(accountRequest.getAccountId());
        account.setBalance(accountDao.getUserBalance(accountRequest.getAccountId()) + accountRequest.getBalance());

        accountDao.changeMoneyInAccount(account);
    }

    @Override
    public void takeMoney(AccountRequest accountRequest) {
        Account account = new Account();
        try {
            int getBalance = accountDao.getUserBalance(accountRequest.getAccountId());
            if ((getBalance - accountRequest.getBalance()) < 0) {
                throw new SQLException("Недостаточно денег на счете");
            } else {
                account.setId(accountRequest.getAccountId());
                account.setBalance(getBalance - accountRequest.getBalance());
                accountDao.changeMoneyInAccount(account);
            }
        } catch (SQLException e) {
            System.out.println("Невозможно снять деньги");
        }
    }

    @Override
    public int linkBalance(int accountId) {
        balance = accountDao.getUserBalance(accountId);
        return balance;
    }

    @Override
    public void transactionToAccount(TransactionRequest transactionRequest) {
        Account account = new Account();
        Account account1 = new Account();
        Transaction transaction = new Transaction();
        try {
            int getBalance = accountDao.getUserBalance(transactionRequest.getAccountIdUser1());
            int takeAccountBalance = accountDao.takeBalanceAccount(transactionRequest.getAccountNumberUser2());
            int takeIdAccount = accountDao.takeIdAccount(transactionRequest.getAccountNumberUser2());
            if ((getBalance - transactionRequest.getSumMoney()) < 0) {
                throw new SQLException("Недостаточно денег на счете");
            } else {
                account.setId(transactionRequest.getAccountIdUser1());
                account.setBalance(getBalance - transactionRequest.getSumMoney());
                accountDao.changeMoneyInAccount(account);
                account1.setId(takeIdAccount);
                account1.setBalance(takeAccountBalance + transactionRequest.getSumMoney());
                accountDao.changeMoneyInAccount(account1);

                transaction.setIdUserFrom(userDao.getUserInfo(transactionRequest.getAccountIdUser1()));
                transaction.setIdUserTo(userDao.getUserInfo(takeIdAccount));
                transaction.setMoney(transactionRequest.getSumMoney());
                accountDao.saveTransaction(transaction);
            }
        } catch (SQLException e) {
            System.out.println("Невозможно снять деньги");
        }
    }


}

