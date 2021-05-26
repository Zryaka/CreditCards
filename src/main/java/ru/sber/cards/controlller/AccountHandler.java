package ru.sber.cards.controlller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import ru.sber.cards.dao.models.AccountRequest;
import ru.sber.cards.service.AccountService;

import java.io.IOException;
import java.io.OutputStream;

public class AccountHandler implements HttpHandler {

    private final AccountService accountService;
    private final ObjectMapper objectMapper;

    public AccountHandler(AccountService accountService) {
        this.accountService = accountService;
        this.objectMapper = new ObjectMapper();
    }

    private final String success = "Executed!";
    private final String error = "Error account!";
    private final String errorGet = "Get method not found for this context";
    private final String errorPost = "POST method not found for this context";
    private final String errorNotMethod = "Method not found";

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if (exchange.getRequestMethod().equals("POST")) {
            AccountRequest accountRequest = objectMapper.readValue(exchange.getRequestBody(),
                    AccountRequest.class);
            exchange.sendResponseHeaders(200, success.length());
            try {
                if (exchange.getRequestURI().getPath().equals("/account/create")) {
                    accountService.createAccount(accountRequest);
                    try (OutputStream outputStream = exchange.getResponseBody()) {
                        outputStream.write(success.getBytes());
                    }
                } else if (exchange.getRequestURI().getPath().equals("/account/add")) {
                    accountService.addMoney(accountRequest);
                    try (OutputStream outputStream = exchange.getResponseBody()) {
                        outputStream.write(success.getBytes());
                    }
                } else if (exchange.getRequestURI().getPath().equals("/account/withdraw")) {
                    accountService.takeMoney(accountRequest);

                    try (OutputStream outputStream = exchange.getResponseBody()) {
                        outputStream.write(success.getBytes());
                    }
                } else {
                    try (OutputStream outputStream = exchange.getResponseBody()) {
                        outputStream.write(errorPost.getBytes());
                    }
                }
            } catch (Exception e) {
                try (OutputStream outputStream = exchange.getResponseBody()) {
                    outputStream.write(error.getBytes());
                }
            }

        } else if (exchange.getRequestMethod().equals("GET")) {
            if (exchange.getRequestURI().getPath().equals("/account/balance")) {
                int accountId = Integer.parseInt(exchange
                        .getRequestURI()
                        .toString()
                        .split("\\?")[1]
                        .split("=")[1]);

                String balance = Integer.toString(accountService.linkBalance(accountId));

                exchange.sendResponseHeaders(200, balance.length());
                try (OutputStream outputStream = exchange.getResponseBody()) {
                    outputStream.write(balance.getBytes());
                } catch (Exception e) {
                    try (OutputStream outputStream = exchange.getResponseBody()) {
                        outputStream.write(error.getBytes());
                    }
                }
            } else {
                try (OutputStream outputStream = exchange.getResponseBody()) {
                    outputStream.write(errorGet.getBytes());
                }
            }
        } else {
            exchange.sendResponseHeaders(200, errorNotMethod.length());
            try (OutputStream outputStream = exchange.getResponseBody()) {
                outputStream.write(errorNotMethod.getBytes());
            }
        }

    }
}