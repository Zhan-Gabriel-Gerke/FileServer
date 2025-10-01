package com.example.client;

import com.example.client.command.*;
import com.example.client.command.CommandController;
import com.example.client.network.ClientConnection;
import com.example.client.service.FileService;

import java.io.IOException;
import java.util.Scanner;

public class MainClient {
    public static void main(String[] args) throws IOException {

        FileService fileService = new FileService();
        CommandController controller = new CommandController(fileService);

        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.print("Enter action (1-get, 2-put, 3-delete, exit): ");
            String action = sc.nextLine();

            Command command = switch (action) {
                case "1" -> new GetCommand();
                case "2" -> new PutCommand();
                case "3" -> new DeleteCommand();
                case "exit" -> new ExitCommand();
                default -> null;
            };

            if (command != null) {
                controller.runCommand(command, sc);
                if (command instanceof ExitCommand) {
                    System.out.println("Exiting...");
                    break;
                }
            } else {
                System.out.println("Unknown command.");
            }
        }

        sc.close();
    }
}