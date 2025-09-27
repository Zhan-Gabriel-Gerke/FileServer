package com.example.server.command;

import com.example.server.model.Request;
import com.example.server.model.Response;
import com.example.server.util.FileUtils;

public class PutCommand implements Command{

    @Override
    public Response execute(Request request) {
        FileUtils result = FileUtils.createFile(request.getSecondArg(), request.getData());
        if (result.isSuccess()){
            return new Response("200 Id " + result.getId());
        }else {
            return new Response("404");
        }
    }
}
