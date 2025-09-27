package com.example.server.command;

import com.example.server.model.Request;
import com.example.server.model.Response;
import com.example.server.util.FileUtils;
import com.example.server.util.JsonUtils;

import java.io.File;

public class DeleteCommand implements Command {
    private final Request request;

    public DeleteCommand(Request request) {
        this.request = request;
    }

    @Override
    public Response execute(Request request) {
        if (request.getFirsArg().equals("BY_NAME")){
            return new Response(FileUtils.deleteFileByName(request.getSecondArg()));
        }else {
            File file = JsonUtils.getFile(Integer.parseInt(request.getSecondArg()));
            return new Response(FileUtils.deleteFileByFile(file));
        }
    }
}
