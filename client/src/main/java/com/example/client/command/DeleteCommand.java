package com.example.client.command;

import com.example.client.model.Request;
import com.example.client.model.Respond;
import com.example.client.model.ActionType;
import com.example.client.service.FileService;

import java.util.Scanner;

public class DeleteCommand implements Command {

    @Override
    public void execute(FileService service, Scanner sc) {
        System.out.print("Delete by name or id? (1-name, 2-id): ");
        int choice = sc.nextInt();
        sc.nextLine();

        String identifier = (choice == 1) ? prompt(sc, "Enter filename: ") : prompt(sc, "Enter id: ");
        String arg = (choice == 1) ? "name" : "id";

        Request request = new Request.Builder()
                .type(ActionType.DELETE)
                .identifier(identifier)
                .arg(arg)
                .build();

        Respond respond = service.sendRequest(request);
        System.out.println(respond.getMessage());
    }

    private String prompt(Scanner sc, String message) {
        System.out.print(message);
        return sc.nextLine();
    }
}
