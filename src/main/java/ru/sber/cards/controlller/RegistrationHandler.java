package ru.sber.cards.controlller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import ru.sber.cards.dao.models.CardResponse;
import ru.sber.cards.dao.models.RegistrationRequest;
import ru.sber.cards.service.UserService;


import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public class RegistrationHandler implements HttpHandler {

    private final UserService userService;
    private final ObjectMapper objectMapper;

    public RegistrationHandler(UserService userService) {
        this.userService = userService;
        this.objectMapper = new ObjectMapper();
    }

    private final String success = "Registration completed!";
    private final String error = "Error!";
    private final String errorGet = "Get method not found for this context";

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        if (httpExchange.getRequestMethod().equals("POST")) {
            RegistrationRequest registrationRequest = objectMapper.readValue(httpExchange.getRequestBody(),
                    RegistrationRequest.class);
            httpExchange.sendResponseHeaders(200, success.length());
            try {
                userService.registration(registrationRequest);
                try (OutputStream outputStream = httpExchange.getResponseBody()) {
                    outputStream.write(success.getBytes());
                }
            } catch (Exception e) {
                try (OutputStream outputStream = httpExchange.getResponseBody()) {
                    outputStream.write(error.getBytes());
                }
            }
        } else if (httpExchange.getRequestMethod().equals("GET")) {
            httpExchange.sendResponseHeaders(200, errorGet.length());
            try (OutputStream outputStream = httpExchange.getResponseBody()) {
                outputStream.write(errorGet.getBytes());
            }
        }
    }
}

