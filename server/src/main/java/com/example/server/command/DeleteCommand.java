package com.example.server.command;

import com.example.server.model.Request;
import com.example.server.model.Response;
import com.example.server.util.FileUtils;
import com.example.server.util.JsonUtils;

import java.io.File;

public class DeleteCommand implements Command {

    @Override
    public Response execute(Request request) {
        if (request.getFirsArg().equals("BY_NAME")){
            FileUtils fileUtils = FileUtils.deleteFileByName(request.getSecondArg());
            JsonUtils.deleteRecordByFile(fileUtils.getFile());
            if (fileUtils.isSuccess()){
                return new Response("200");
            }
        }else if (request.getFirsArg().equals("BY_ID")){
            File file = JsonUtils.getFile(Integer.parseInt(request.getSecondArg()));
            FileUtils fileUtils = FileUtils.deleteFileByFile(file);
            if (fileUtils.isSuccess()){
                return new Response("200");
            }
        }
        return new Response("404");
    }
}
