package com.example.server.model;

import com.example.server.network.ServerConnection;

public class Request {

    private final String type;
    private final String firstArg;
    private String secondArg;
    private final ServerConnection connection;

    public Request(String request, ServerConnection connection) {
        this.connection = connection;
        String[] array = request.split(" ", 3);

        //parsing request
        String[] parts = request.trim().split("\\s+", 3);

        this.type = parts.length > 0 ? parts[0] : "";
        this.firstArg = parts.length > 1 ? parts[1] : null;
        this.secondArg = parts.length > 2 ? parts[2] : null;
    }

    private void putDoubleSlash(){
        for (int i = 0; i < secondArg.length(); i++){
            if (secondArg.charAt(i) == '\\' && secondArg.charAt(i + 1) != '\\'){
                secondArg = secondArg.substring(0, i) + '\\' + secondArg.substring(i + 1);
                i++;
            }
        }
    }

    public void normalizeSlash(){
        putDoubleSlash();
    }

    public ServerConnection getConnection(){
        return connection;
    }

    public String getType() {
        return type;
    }

    public String getFirstArg() {
        return firstArg;
    }

    public String getSecondArg() {
        return secondArg;
    }
}
