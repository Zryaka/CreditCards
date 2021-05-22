package ru.sber.cards.controlller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import ru.sber.cards.dao.models.Transaction;
import ru.sber.cards.dao.models.TransactionRequest;
import ru.sber.cards.service.AccountService;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public class TransactionHandler implements HttpHandler {

    private final AccountService accountService;
    private final ObjectMapper objectMapper;

    public TransactionHandler(AccountService accountService) {
        this.accountService = accountService;
        this.objectMapper = new ObjectMapper();
    }

    private final String success = "Transaction complete";
    private final String error = "Transaction failed";
    private final String errorGet = "Get method not found for this context";
    private final String errorPost = "POST method not found for this context";

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if (exchange.getRequestMethod().equals("POST")) {
            TransactionRequest transactionRequest = objectMapper.readValue(exchange.getRequestBody(),
                    TransactionRequest.class);
            if (exchange.getRequestURI().getPath().equals("/transaction")) {
                try {
                    exchange.sendResponseHeaders(200, success.length());
                    accountService.transactionToAccount(transactionRequest);
                    try (OutputStream outputStream = exchange.getResponseBody()) {
                        outputStream.write(success.getBytes());
                    }
                } catch (Exception e) {
                    exchange.sendResponseHeaders(200, success.length());
                    try (OutputStream outputStream = exchange.getResponseBody()) {
                        outputStream.write(error.getBytes());
                    }
                }
            } else {
                exchange.sendResponseHeaders(200, errorPost.length());
                try (OutputStream outputStream = exchange.getResponseBody()) {
                    outputStream.write(errorPost.getBytes());
                }
            }
        } else if (exchange.getRequestMethod().equals("GET")) {
            if (exchange.getRequestURI().getPath().equals("/transaction")) {
                List<Transaction> transactions = accountService.linkTransaction();
                String stringTra = objectMapper.writeValueAsString(transactions);

                exchange.sendResponseHeaders(200, stringTra.length());
                try (OutputStream outputStream = exchange.getResponseBody()) {
                    outputStream.write(stringTra.getBytes());
                } catch (Exception e) {
                    try (OutputStream outputStream = exchange.getResponseBody()) {
                        outputStream.write(error.getBytes());
                    }
                }
            } else {
                exchange.sendResponseHeaders(200, errorGet.length());
                try (OutputStream outputStream = exchange.getResponseBody()) {
                    outputStream.write(errorGet.getBytes());
                }
            }
        }
    }
}
