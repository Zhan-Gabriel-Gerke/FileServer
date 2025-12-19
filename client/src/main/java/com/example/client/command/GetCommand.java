package com.example.client.command;

import com.example.client.model.Request;
import com.example.client.model.Respond;
import com.example.client.model.ActionType;
import com.example.client.service.FileService;
import com.example.client.util.FileUtils;

import java.io.File;
import java.util.Scanner;

public class GetCommand implements Command {

    @Override
    public void execute(FileService service, Scanner sc) {
        System.out.print("Get by name or id? (1-name, 2-id): ");
        int choice = sc.nextInt();
        sc.nextLine();

        String identifier = (choice == 1) ? prompt(sc, "Enter filename: ") : prompt(sc, "Enter id: ");
        String arg = (choice == 1) ? "name" : "id";
        String saveName = prompt(sc, "Enter local name to save as: ");
        if (saveName.isEmpty()) saveName = "downloaded_file";

        try {
            File destFile = FileUtils.getSafeObject(saveName);
            Request request = new Request.Builder()
                    .type(ActionType.GET)
                    .identifier(identifier)
                    .arg(arg)
                    .build();
            Respond respond = service.downloadFile(request, destFile);
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
