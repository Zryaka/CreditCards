package ru.sber.cards.controlller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import ru.sber.cards.dao.models.CardRequest;
import ru.sber.cards.dao.models.CardResponse;
import ru.sber.cards.service.CardService;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public class CardHandler implements HttpHandler {

    private final ObjectMapper objectMapper;
    private final CardService cardService;

    public CardHandler(CardService cardService) {
        this.objectMapper = new ObjectMapper();
        this.cardService = cardService;
    }

    private final String success = "Card created!";
    private final String error = "Card not created!";
    private final String errorGet = "Get method not found for this context";
    private final String errorPost = "POST method not found for this context";

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if (exchange.getRequestMethod().equals("POST")) {
            CardRequest cardRequest = objectMapper.readValue(exchange.getRequestBody(),
                    CardRequest.class);
            try {
                if (exchange.getRequestURI().getPath().equals("/card")) {
                    exchange.sendResponseHeaders(200, success.length());
                    cardService.createCard(cardRequest);
                    try (OutputStream outputStream = exchange.getResponseBody()) {
                        outputStream.write(success.getBytes());
                    }
                } else {
                    exchange.sendResponseHeaders(200, errorPost.length());
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
            if (exchange.getRequestURI().getPath().equals("/card")) {
                int accountId = Integer.parseInt(exchange
                        .getRequestURI()
                        .toString()
                        .split("\\?")[1]
                        .split("=")[1]);
                List<CardResponse> cardList = cardService.linkCard(accountId);
                String stringCard= objectMapper.writeValueAsString(cardList);

                exchange.sendResponseHeaders(200, stringCard.length());
                try (OutputStream outputStream = exchange.getResponseBody()) {
                    outputStream.write(stringCard.getBytes());
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
