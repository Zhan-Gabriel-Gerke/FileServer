package com.example.server.command;

import com.example.server.MainServer;
import com.example.server.model.Request;
import com.example.server.model.Response;
import com.example.server.network.ServerConnection;
import com.example.server.util.JsonUtils;

import java.io.IOException;

public class ExitCommand implements Command{

    @Override
    public Response execute (Request request){
        try {
            JsonUtils.save();
            ServerConnection.stopServer();
            MainServer.server.close();
        } catch (IOException e) {
            return new Response("400");
        }
        return new Response("200");

    }
}
