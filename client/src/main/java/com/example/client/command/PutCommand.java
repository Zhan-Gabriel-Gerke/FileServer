package com.example.client.command;

import com.example.client.model.Request;
import com.example.client.model.Respond;
import com.example.client.model.ActionType;
import com.example.client.service.FileService;
import com.example.client.util.FileUtils;

import java.io.File;
import java.util.Scanner;

public class PutCommand implements Command {

    @Override
    public void execute(FileService service, Scanner sc) {
        String fileName = prompt(sc, "Enter local filename: ");
        String serverName = prompt(sc, "Enter name to save on server: ");
        if (serverName.isEmpty()) serverName = fileName;

        try {
            File file = FileUtils.getSafeObject(fileName);
            if (!file.exists()) {
                System.out.println("File not found locally: " + fileName);
                return;
            }
            Request request = new Request.Builder()
                    .type(ActionType.PUT)
                    .identifier(serverName)
                    .build();

            Respond respond = service.uploadFile(request, file);
            System.out.println("Server: " + respond.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String prompt(Scanner sc, String message) {
        System.out.print(message);
        return sc.nextLine();
    }
}
