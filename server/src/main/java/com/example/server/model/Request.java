package com.example.server.model;

public class Request {

    private final String type;
    private final String firsArg;
    private String secondArg;
    private final byte[] data;

    public Request(String request, byte[] data) {
        String[] array = request.split(" ", 3);
        if ("EXIT".equals(array[0])) {
            this.type = "EXIT";
            this.firsArg = null;
            this.secondArg = null;
            this.data = null;
        }else {
            this.type = array[0];
            this.firsArg = array[1];
            this.secondArg = array[2];
            this.data = data;
        }
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
