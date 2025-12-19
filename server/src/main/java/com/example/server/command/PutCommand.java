package com.example.server.command;

import com.example.server.model.Request;
import com.example.server.model.Response;
import com.example.server.util.FileUtils;
import com.example.server.util.JsonUtils;

import javax.imageio.IIOException;
import java.io.File;
import java.io.IOException;

public class PutCommand implements Command{

    @Override
    public Response execute(Request request) {
        String fileName = request.getSecondArg();

        if (fileName == null || fileName.isEmpty()){
            return new Response("400 Bad Request");
        }

        try{
            File file = FileUtils.getFileSafe(fileName);

            if (file.exists()) {
                return new Response("403");
            }

            //receive file
            request.getConnection().receivedFile(file);

            //if everything ok add to database
            int id = JsonUtils.addRecord(file);
            return new Response("200 Id " + id);

        } catch (IOException | SecurityException e){
            e.printStackTrace();
            return new Response("403");
        }
    }
}
