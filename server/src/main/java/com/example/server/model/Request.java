package com.example.server.model;

public class Request {

    private final String type;
    private final String firsArg;
    private final String secondArg;

    public Request(String request){
        String[] array = request.split(" ", 3);
        this.type = array[0];
        this.firsArg = array[1];
        this.secondArg = array[2];
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

}
