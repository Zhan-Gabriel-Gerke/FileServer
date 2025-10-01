package com.example.client.command;

import com.example.client.model.Request;
import com.example.client.model.Respond;
import com.example.client.model.ActionType;
import com.example.client.service.FileService;
import com.example.client.util.FileUtils;

import java.util.Scanner;

public class GetCommand implements Command {

    @Override
    public void execute(FileService service, Scanner sc) {
        System.out.print("Get by name or id? (1-name, 2-id): ");
        int choice = sc.nextInt();
        sc.nextLine();

        String identifier = (choice == 1) ? prompt(sc, "Enter filename: ") : prompt(sc, "Enter id: ");
        String arg = (choice == 1) ? "BY_NAME" : "BY_ID";

        Request request = new Request.Builder()
                .type(ActionType.GET)
                .identifier(identifier)
                .arg(arg)
                .build();

        Respond respond = service.sendRequest(request);

        if (respond.getData() != null && respond.getData().length > 0) {
            String saveName = prompt(sc, "File downloaded! Specify local name: ");
            if (saveName.isEmpty()) saveName = identifier;
            FileUtils.writeFile(saveName, respond.getData());
        }

        System.out.println(respond.getMessage());
    }

    private String prompt(Scanner sc, String message) {
        System.out.print(message);
        return sc.nextLine();
    }
}
