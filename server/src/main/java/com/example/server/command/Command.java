package com.example.server.command;

import com.example.server.model.Request;
import com.example.server.model.Response;

public interface Command {
    Response execute(Request request);
}
