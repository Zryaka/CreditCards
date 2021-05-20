package ru.sber.cards.controlller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import ru.sber.cards.dao.models.CardResponse;
import ru.sber.cards.dao.models.LoginRequest;
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

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if (exchange.getRequestMethod().equals("POST")) {
            TransactionRequest transactionRequest = objectMapper.readValue(exchange.getRequestBody(),
                    TransactionRequest.class);
            exchange.sendResponseHeaders(200, success.length());
            try {
                accountService.transactionToAccount(transactionRequest);
                try (OutputStream outputStream = exchange.getResponseBody()) {
                    outputStream.write(success.getBytes());
                }
            } catch (Exception e) {
                try (OutputStream outputStream = exchange.getResponseBody()) {
                    outputStream.write(error.getBytes());
                }
            }
/*        } else if (exchange.getRequestMethod().equals("GET")) {
            List<> cardList =
            ArrayNode array = objectMapper.valueToTree(cardList);
            JsonNode result = objectMapper.createObjectNode().set("cards", array);

            String stringResult = result.toString();

            exchange.sendResponseHeaders(200, stringResult.length());
            try (OutputStream outputStream = exchange.getResponseBody()) {
                outputStream.write(stringResult.getBytes());
            } catch (Exception e) {
                try (OutputStream outputStream = exchange.getResponseBody()) {
                    outputStream.write(error.getBytes());
                }
            }*/

        }
    }
}
