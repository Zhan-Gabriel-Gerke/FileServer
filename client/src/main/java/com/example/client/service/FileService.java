package com.example.client.service;

import com.example.client.model.Request;
import com.example.client.model.Respond;
import com.example.client.network.ClientConnection;

import java.io.IOException;

public class FileService {

    public FileService() {
    }

    public Respond sendRequest(Request request) {
        try (ClientConnection connection = ClientConnection.startClient()) {
            connection.sendMessage(requestToString(request));

            if (request.getData() != null && request.getData().length > 0) {
                connection.sendFile(request.getData());
            }

            String message = connection.getMessage();
            byte[] data = null;

            if (request.getType().name().equals("GET") && message.startsWith("200")) {
                data = connection.getFile();
            }

            return new Respond(message, data);

        } catch (IOException e) {
            e.printStackTrace();
            return new Respond("Error: " + e.getMessage());
        }
    }

    private String requestToString(Request request) {
        String identifier = request.getIdentifier() != null ? request.getIdentifier() : "";
        String arg = request.getArg() != null ? request.getArg() : "";

        return switch (request.getType()) {
            case GET -> "GET " + arg + " " + identifier;
            case PUT -> "PUT SPECIAL_NAME " + identifier;
            case DELETE -> "DELETE " + arg + " " + identifier;
            case EXIT -> "EXIT";
        };
    }
}