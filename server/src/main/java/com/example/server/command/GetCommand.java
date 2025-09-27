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
        if (request.getFirsArg().equals("BY_NAME")){
            request.normalizeSlash();
            fileUtils = FileUtils.getDataByFileName(request.getSecondArg());
        }else if (request.getFirsArg().equals("BY_ID")){
            file = JsonUtils.getFile(Integer.parseInt(request.getSecondArg()));
            fileUtils = FileUtils.getDataByFile(file);
            
            String fileName = file.getName();
            
        }else {
            return new Response("404");
        }
        if (fileUtils.isSuccess()){
            if (fileUtils.getData() != null){
                return new Response("200 " + file.getName(), fileUtils.getData());
            }
        }
        return new Response("404");
    }
}
