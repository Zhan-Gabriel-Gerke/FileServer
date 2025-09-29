package com.example.client.model;

public class Respond {
    private final String message;
    private byte[] data;

    public Respond(String message, byte[] data) {
        this.message = message;
        this.data = data;
    }

    public Respond(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public byte[] getData() {
        return data;
    }

}
