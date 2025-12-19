package com.example.client.model;

public class Request {

    private final ActionType type;        // Type
    private final String identifier;      // ID or Name
    private final String arg;             // By name or id

    private Request(Builder builder) {
        this.type = builder.type;
        this.identifier = builder.identifier;
        this.arg = builder.arg;
    }

    // Getters
    public ActionType getType() { return type; }
    public String getIdentifier() { return identifier; }
    public String getArg() { return arg; }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(type);
        if (arg != null && !arg.isEmpty()) sb.append(" ").append(arg);
        if (identifier != null && !identifier.isEmpty()) sb.append(" ").append(identifier);
        return sb.toString();
    }

    // Builder
    public static class Builder {
        private ActionType type;
        private String identifier;
        private String arg;

        public Builder type(ActionType type) { this.type = type; return this; }
        public Builder identifier(String identifier) { this.identifier = identifier; return this; }
        public Builder arg(String arg) { this.arg = arg; return this; }
        public Request build() {
            return new Request(this);
        }
    }
}
