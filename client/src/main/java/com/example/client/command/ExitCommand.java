package com.example.client.command;

import com.example.client.model.Request;
import com.example.client.model.ActionType;
import com.example.client.service.FileService;

import java.util.Scanner;

public class ExitCommand implements Command {

    @Override
    public void execute(FileService service, Scanner sc) {
        Request request = new Request.Builder()
                .type(ActionType.EXIT)
                .build();

        service.sendRequest(request);
        System.out.println("Exiting...");
        System.exit(0);
    }
}
