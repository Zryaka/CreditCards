package ru.sber.cards.controlller;

import com.sun.net.httpserver.HttpServer;
import ru.sber.cards.dao.*;
import ru.sber.cards.service.*;

import java.io.IOException;
import java.net.InetSocketAddress;

public class BaseServer {

    public void startServer() throws IOException{
        HttpServer server = HttpServer.create(new InetSocketAddress(8080),0);
        UserDao userDao = new UserDaoImpl();
        AccountDao accountDao = new AccountDaoImpl();
        AccountService accountService = new AccountServiceImpl(userDao,accountDao);
        UserService userService = new UserServiceImpl(userDao);
        CardDao cardDao = new CardDaoIml();
        CardService cardService = new CardServiceImp(cardDao);

        server.createContext("/register", new RegistrationHandler(userService));
        server.createContext("/login",new LoginHandler(userService));
        server.createContext("/card",new CardHandler(cardService));
        server.createContext("/account",new AccountHandler(accountService));
        server.createContext("/transaction", new TransactionHandler(accountService));
        server.start();
    }
}
