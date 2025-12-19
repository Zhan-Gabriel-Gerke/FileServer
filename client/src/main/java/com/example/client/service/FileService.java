package com.example.client.service;

import com.example.client.model.Request;
import com.example.client.model.Respond;
import com.example.client.network.ClientConnection;
import com.example.client.model.ActionType;

import java.io.File;
import java.io.IOException;

public class FileService {

    public FileService() {
    }

    public Respond sendRequest(Request request) {
        return execute(conn -> {
            conn.sendMessage(formatRequest(request));
            return new Respond(conn.getMessage(), null);
        });
    }

    public Respond uploadFile(Request request, File destFile) {
        return execute(conn -> {
            conn.sendMessage(formatRequest(request));       //text
            conn.sendFile(destFile);                        //bytes
            return new Respond(conn.getMessage(), null);
        });
    }

    public Respond downloadFile(Request request, File destFile) {
        return execute(conn -> {
            conn.sendMessage(formatRequest(request));
            String response = conn.getMessage();

            if (response.startsWith("200")) {
                conn.getFile(destFile);
                return new Respond("200", null);
            }
            return new Respond(response, null);
        });
    }

    private Respond execute(ConnectionAction action) {
        try (ClientConnection connection = ClientConnection.startClient()) {
            return action.apply(connection);
        } catch (IOException e) {
            e.printStackTrace();
            return new Respond("Error: " + e.getMessage(), null);
        }
    }

    private String formatRequest(Request request) {
        String id = request.getIdentifier() == null ? "" : request.getIdentifier();
        String arg = request.getArg() == null ? "" : request.getArg();

        return switch (request.getType()) {
            case GET -> "GET " + arg + " " + id;
            case PUT -> "PUT FILE_NAME " + id;
            case DELETE -> "DELETE " + arg + " " + id;
            case EXIT -> "EXIT";
        };
    }

    @FunctionalInterface
    private interface ConnectionAction {
        Respond apply(ClientConnection conn) throws IOException;
    }
}
