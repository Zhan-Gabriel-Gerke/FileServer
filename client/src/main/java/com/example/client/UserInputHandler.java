package com.example.client;

import com.example.client.command.ActionType;

import java.util.Scanner;

public class UserInputHandler {

    private static final Scanner scanner = new Scanner(System.in);

    public static ActionType printInnerInfo(){
        System.out.print("Enter action (1 - get a file, 2 - create a file, 3 - delete a file): ");
        String actionString = scanner.nextLine();
        return ActionType.fromString(actionString);
    }

    public void shutDown(){
        System.out.println("The request was sent.");
    }

}
