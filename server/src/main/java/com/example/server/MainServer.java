package com.example.server;

import com.example.server.command.DeleteCommand;
import com.example.server.command.ExitCommand;
import com.example.server.command.GetCommand;
import com.example.server.command.PutCommand;
import com.example.server.controller.CommandController;
import com.example.server.model.Request;
import com.example.server.model.Response;
import com.example.server.network.ServerConnection;
import com.example.server.util.JsonUtils;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;

public class MainServer {
    private static final Map<Integer, File> treeMap = JsonUtils.getMap();
    public static ServerSocket server;
    public static void main(String[] args) throws IOException {
        System.out.println("Server started!");
        final String address = "127.0.0.1"; // Адрес сервера (локальный)
        final int port = 23456; // Порт сервера
        server = new ServerSocket(port, 50, InetAddress.getByName(address));
        try {
            while (ServerConnection.isServerRunning()) {
                Socket clientSocker = server.accept();
                new Thread(() -> {
                    try {
                        start(clientSocker);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }).start();
            }
        } catch (IOException ignored) {
        }
    }
    private static void start(Socket clientSocker) throws IOException {
        ServerConnection connection = new ServerConnection(clientSocker);
        while (!connection.isClosed()) {
            String receivedRequest;
            try {
                receivedRequest = connection.getInput();
            } catch (IOException e) {
                connection.close();
                break;
            }
            byte[] fileBytes = new byte[0];
            CommandController controller = new CommandController();
            switch (receivedRequest.split("\\s+")[0]) {
                case "EXIT"://++
                    controller.setCommand(new ExitCommand());
                    break;
                case "PUT":
                    fileBytes = connection.getFile();
                    controller.setCommand(new PutCommand());
                    break;
                case "DELETE":
                    controller.setCommand(new DeleteCommand());
                    break;
                case "GET":
                    controller.setCommand(new GetCommand());
                    break;
            }
            Request request = new Request(receivedRequest, fileBytes);
            Response response = controller.execute(request);
            //send data to a client
            connection.sendMessage(response.getMessage());
            if (response.getData() != null) {
                connection.sendFile(response.getData());
            }
            connection.close();
        }
    }
}