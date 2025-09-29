package com.example.client.command;

import com.example.client.model.Request;
import com.example.client.model.Respond;

public interface Command {
    Respond execute(Request request);
}
