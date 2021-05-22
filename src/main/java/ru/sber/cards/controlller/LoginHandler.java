package ru.sber.cards.controlller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import ru.sber.cards.dao.models.LoginRequest;
import ru.sber.cards.service.UserService;

import java.io.IOException;
import java.io.OutputStream;

public class LoginHandler implements HttpHandler {

    private final ObjectMapper objectMapper;
    private final UserService userService;

    public LoginHandler(UserService userService) {
        this.objectMapper = new ObjectMapper();
        this.userService = userService;
    }

    private final String success = "Login!";
    private final String error = "Not login!";
    private final String errorGet = "Get method not found for this context";
    private final String errorPost = "POST method not found for this context";

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if (exchange.getRequestMethod().equals("POST")) {
            LoginRequest loginRequest = objectMapper.readValue(exchange.getRequestBody(),
                    LoginRequest.class);
            try {
                if (exchange.getRequestURI().getPath().equals("/login")) {
                    exchange.sendResponseHeaders(200, success.length());
                    userService.login(loginRequest);
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
                exchange.sendResponseHeaders(200, error.length());
                try (OutputStream outputStream = exchange.getResponseBody()) {
                    outputStream.write(error.getBytes());
                }
            }
        } else if (exchange.getRequestMethod().equals("GET")) {
            exchange.sendResponseHeaders(200, errorGet.length());
            try (OutputStream outputStream = exchange.getResponseBody()) {
                outputStream.write(errorGet.getBytes());
            }
        }
    }
}
