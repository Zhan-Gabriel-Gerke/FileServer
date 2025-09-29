package com.example.client;

import com.example.client.command.ActionType;
import com.example.client.network.ClientConnection;
import com.example.client.util.FileUtils;

import javax.swing.*;
import java.io.IOException;
import java.util.Scanner;

public class MainClient {
    public static void main(String[] args) throws IOException {
        final ClientConnection connection = ClientConnection.startClient();
        final Scanner sc = new Scanner(System.in);
        ActionType actionType = UserInputHandler.printInnerInfo();//asks to choose an action
        String actionStringTemp = sc.nextLine();
        //int action = 0;
        if (actionType == ActionType.EXIT){
            connection.sendMessage("EXIT");

            System.exit(0);
        } /*else if (actionStringTemp.equals("1") || actionStringTemp.equals("2") || actionStringTemp.equals("3")){
            action = Integer.parseInt(actionStringTemp);
        }*/
        String identifier = "";
        String specialNameForFile = "";
        if (actionType == ActionType.GET || actionType == ActionType.DELETE){
            System.out.printf("Do you want to %s the file by name or by id (1 - name, 2 - id): ", actionType == ActionType.GET ? "get" : "delete");
            int nameOrId = sc.nextInt();
            sc.nextLine();
            if (nameOrId == 1){
                System.out.print("Enter filename: ");
                identifier = sc.nextLine();
            } else if (nameOrId == 2){
                System.out.print("Enter id: ");
                identifier = sc.nextLine();
            }
        } else if (actionType == ActionType.PUT){
            System.out.print("Enter name of the file: ");
            identifier = sc.nextLine();
            System.out.print("Enter name of the file to be saved on server: ");
            specialNameForFile = sc.nextLine();
        }
        if (actionType == ActionType.GET || actionType == ActionType.DELETE){
            String request = createRequest(actionType, identifier);
            try{
                connection.sendMessage(request);
                System.out.println("The request was sent.");
            } catch (Exception e){
                System.out.println("An error occurred.");
            }
        }
        else if (actionType == ActionType.PUT){
            if (specialNameForFile.isEmpty()){
                specialNameForFile = identifier;
            }
            String request = createRequest(actionType, specialNameForFile);
            connection.sendMessage(request);
            connection.sendFile(FileUtils.getFile(identifier));
            System.out.println("The request was sent.");
        }
        String respond = connection.getMessage();
        if (actionType == ActionType.GET){
            if (respond.startsWith("200")){
                byte[] byteFile = connection.getFile();
                System.out.print("The file was downloaded! Specify a name for it:");
                String name = sc.nextLine();
                if (name.isEmpty()){
                    name = respond.substring(4);
                }
                FileUtils.writeFile(name, byteFile);
            }
            String result = convertRespond(respond, actionType);
            System.out.println(result);
            connection.close();
        } else if (actionType == ActionType.PUT || actionType == ActionType.DELETE){
            String result = convertRespond(respond, actionType);
            System.out.println(result);
            connection.close();
        }
    }

    public static String convertRespond(String respond, ActionType actionType){
        String result = "";
        boolean isSuccess = respond.startsWith("200");
        if (actionType == ActionType.GET){
            //GET
            if (isSuccess){
                result = "File saved on the hard drive!";
            } else if (respond.startsWith("404")){
                result = "The response says that the file was not found!";
            }
        } else if (actionType == ActionType.PUT){
            //PUT
            if (isSuccess){
                int id = Integer.parseInt(respond.substring(respond.indexOf(" Id ") + 4));
                result = "Response says that file is saved! ID = " + id;
            } else {
                result = "The response says that the file was not found!";
            }
        } else if (actionType == ActionType.DELETE){
            //DELETE
            if (isSuccess){
                result = "The response says that the file was successfully deleted!";
            } else {
                result = "The response says that the file was not found!";
            }
        }
        return result;
    }

    public static String createRequest(ActionType actionType, String identifier) {
        String request;
        if (identifier.matches("\\d+")){
            request = switch (actionType) {
                case EXIT -> "EXIT";
                case GET -> "GET " + "BY_ID " + identifier;//2
                case PUT -> "PUT" + " SPECIAL_NAME " + identifier;
                case DELETE -> "DELETE " + "BY_ID "+ identifier;//2
            };
        }else{
            request = switch (actionType) {
                case EXIT -> "EXIT";
                case GET -> "GET " + "BY_NAME " + identifier;//2
                case PUT -> "PUT" + " SPECIAL_NAME " + identifier;
                case DELETE -> "DELETE " + "BY_NAME "+ identifier;//2
            };
        }
        return request;
    }
}