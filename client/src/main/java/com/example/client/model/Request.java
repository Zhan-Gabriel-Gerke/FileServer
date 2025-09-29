package com.example.client.model;

public class Request {


    private String type;//Type
    private String identifier;//ID or Name
    private byte[] data;//data
    private String arg;//By name or id

    private Request(Builder builder) {
        this.type = builder.type;
        this.identifier = builder.identifier;
        this.data = builder.data;
        this.arg = builder.arg;
    }

    public static class Builder {

        private String type;//Type
        private String identifier;//ID or Name
        private byte[] data;//data
        private String arg;//By name or id


        public Builder type(String type){
            this.type = type;
            return this;
        }

        public Builder identifier(String identifier){
            this.identifier = identifier;
            return this;
        }

        public Builder data(byte[] data){
            this.data = data;
            return this;
        }

        public Builder arg(String arg){
            this.arg = arg;
            return this;
        }

        public Request build(){
            return new Request(this);
        }

    }
}

/*
Request request = new Request.Builder()
        .type("type")
        .identifier("identifier")
        .data(data[] data)
        .arg("arg")
        .build();
 */