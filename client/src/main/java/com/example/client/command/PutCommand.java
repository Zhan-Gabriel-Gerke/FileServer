package com.example.client.command;

import com.example.client.model.Request;
import com.example.client.model.Respond;
import com.example.client.model.ActionType;
import com.example.client.service.FileService;
import com.example.client.util.FileUtils;

import java.util.Scanner;

public class PutCommand implements Command {

    @Override
    public void execute(FileService service, Scanner sc) {
        String fileName = prompt(sc, "Enter local filename: ");
        String serverName = prompt(sc, "Enter name to save on server: ");
        if (serverName.isEmpty()) serverName = fileName;

        byte[] data = FileUtils.getFile(fileName);

        Request request = new Request.Builder()
                .type(ActionType.PUT)
                .identifier(serverName)
                .data(data)
                .build();

        Respond respond = service.sendRequest(request);
        System.out.println(respond.getMessage());
    }

    private String prompt(Scanner sc, String message) {
        System.out.print(message);
        return sc.nextLine();
    }
}
