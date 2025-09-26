package com.example.server.controller;

import com.example.server.command.Command;
import com.example.server.model.Request;
import com.example.server.model.Response;

public class CommandController {
    private Command command;

    public void setCommand(Command command) {
        this.command = command;
    }

    public Response execute(Request request){
        return command.execute(request);
    }
}
