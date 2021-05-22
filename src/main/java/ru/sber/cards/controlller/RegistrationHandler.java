package ru.sber.cards.controlller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import ru.sber.cards.dao.models.RegistrationRequest;
import ru.sber.cards.service.UserService;


import java.io.IOException;
import java.io.OutputStream;

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
    private final String errorPost = "POST method not found for this context";

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        if (httpExchange.getRequestMethod().equals("POST")) {
            RegistrationRequest registrationRequest = objectMapper.readValue(httpExchange.getRequestBody(),
                    RegistrationRequest.class);
            try {
                if (httpExchange.getRequestURI().getPath().equals("/register")) {
                    httpExchange.sendResponseHeaders(200, success.length());
                    userService.registration(registrationRequest);
                    try (OutputStream outputStream = httpExchange.getResponseBody()) {
                        outputStream.write(success.getBytes());
                    }
                } else {
                    httpExchange.sendResponseHeaders(200, errorPost.length());
                    try (OutputStream outputStream = httpExchange.getResponseBody()) {
                        outputStream.write(errorPost.getBytes());
                    }
                }
            } catch (Exception e) {
                httpExchange.sendResponseHeaders(200, error.length());
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

