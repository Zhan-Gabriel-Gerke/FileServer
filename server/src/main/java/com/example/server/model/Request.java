package com.example.server.model;

public class Request {

    private final String type;
    private final String firsArg;
    private final String secondArg;
    private final byte[] data;

    public Request(String request) {
        this(request, null);
    }

    public Request(String request, byte[] data) {
        String[] array = request.split(" ", 3);
        this.type = array[0];
        this.firsArg = array[1];
        this.secondArg = array[2];
        this.data = data;
    }

    public String getType() {
        return type;
    }

    public String getFirsArg() {
        return firsArg;
    }

    public String getSecondArg() {
        return secondArg;
    }

    public byte[] getData() {
        return data;
    }
}
