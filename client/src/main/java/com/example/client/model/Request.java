package com.example.client.model;

public class Request {

    private final ActionType type;        // Type
    private final String identifier;      // ID or Name
    private final byte[] data;            // Data
    private final String arg;             // By name or id
    private final String nameForReceiver; // Optional

    private Request(Builder builder) {
        this.type = builder.type;
        this.identifier = builder.identifier;
        this.data = builder.data;
        this.arg = builder.arg;
        this.nameForReceiver = builder.nameForReceiver;
    }

    // Геттеры для всех полей
    public ActionType getType() { return type; }
    public String getIdentifier() { return identifier; }
    public byte[] getData() { return data; }
    public String getArg() { return arg; }
    public String getNameForReceiver() { return nameForReceiver; }

    // Builder
    public static class Builder {
        private ActionType type;
        private String identifier;
        private byte[] data;
        private String arg;
        private String nameForReceiver;

        public Builder type(ActionType type) { this.type = type; return this; }
        public Builder identifier(String identifier) { this.identifier = identifier; return this; }
        public Builder data(byte[] data) { this.data = data; return this; }
        public Builder arg(String arg) { this.arg = arg; return this; }
        public Builder nameForReceiver(String nameForReceiver) { this.nameForReceiver = nameForReceiver; return this; }

        public Request build() {
            return new Request(this);
        }
    }
}
