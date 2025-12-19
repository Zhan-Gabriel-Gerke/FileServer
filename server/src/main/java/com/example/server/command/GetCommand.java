package com.example.server.command;

import com.example.server.model.Request;
import com.example.server.model.Response;
import com.example.server.util.FileUtils;
import com.example.server.util.JsonUtils;

import java.io.File;

public class GetCommand implements Command {

    @Override
    public Response execute(Request request){
        FileUtils fileUtils;
        File file = null;
        if (request.getFirstArg().equals("name")){
            request.normalizeSlash();
            fileUtils = FileUtils.getDataByFileName(request.getSecondArg());
            file = fileUtils.getFile();
        }else if (request.getFirstArg().equals("id")){
            file = JsonUtils.getFile(Integer.parseInt(request.getSecondArg()));
            if (file == null) {
                return new Response("404");
            }
            fileUtils = FileUtils.getDataByFile(file);
        }else {
            return new Response("404");
        }
        if (fileUtils.isSuccess()){
            if (fileUtils.getData() != null){
                return new Response("200", fileUtils.getData());
            }
        }
        return new Response("404");
    }
}
