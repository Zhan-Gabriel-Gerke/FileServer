package com.example.client.model;

public enum ActionType {
    GET("1"),
    PUT("2"),
    DELETE("3"),
    EXIT("exit");

    private final String input;

    ActionType(String input) {
        this.input = input;
    }

    public String getInput(String input) {
        return input;
    }

    public static ActionType fromString(String input){
        for (ActionType actionType : values()) {
            if (actionType.input.equalsIgnoreCase(input)) {
                return actionType;
            }
        }
        throw new IllegalArgumentException("Wrong input, No enum constant with code " + input);
    }
}
