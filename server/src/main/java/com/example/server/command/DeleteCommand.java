package com.example.server.command;

import com.example.server.model.Request;
import com.example.server.model.Response;
import com.example.server.util.FileUtils;
import com.example.server.util.JsonUtils;

import java.io.File;

public class DeleteCommand implements Command {

    @Override
    public Response execute(Request request) {
        if (request.getFirstArg().equals("name")){
            FileUtils fileUtils = FileUtils.deleteFileByName(request.getSecondArg());
            JsonUtils.deleteRecordByFile(fileUtils.getFile());
            if (fileUtils.isSuccess()){
                return new Response("200");
            }
        }else if (request.getFirstArg().equals("id")){
            int id = Integer.parseInt(request.getSecondArg());
            File file = JsonUtils.getFile(id);
            if (file == null) {
                return new Response("404");
            }
            FileUtils fileUtils = FileUtils.deleteFileByFile(file);
            JsonUtils.deleteRecordById(id);
            if (fileUtils.isSuccess()){
                return new Response("200");
            }
        }
        return new Response("404");
    }
}
