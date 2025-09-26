package com.example.server.model;

public class Response {

    private final String message;
    private final byte[] data;

    public Response(String message, byte[] data) {
        this.message = message;
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public byte[] getData() {
        return data;
    }
}
