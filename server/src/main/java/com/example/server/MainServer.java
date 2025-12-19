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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainServer {
    // Initialize database on startup
    private static final Map<Integer, File> treeMap = JsonUtils.getMap();
    public static ServerSocket server;

    // Thread pool for handling clients
    private static final ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 2);

    public static void main(String[] args) throws IOException {
        System.out.println("Server started!");
        final String address = "127.0.0.1";
        final int port = 23456;

        server = new ServerSocket(port, 50, InetAddress.getByName(address));

        try {
            while (ServerConnection.isServerRunning()) {
                //Waiting for client connection
                Socket clientSocket = server.accept();
                //Pass client to worker thread
                executor.submit(() -> handleClient(clientSocket));
            }
        } catch (IOException e){
            //Socket closed with exit command
            System.out.println("Server stopped.");
        } finally {
            executor.shutdown();
        }
    }

    private static void handleClient(Socket clientSock) {
        try (ServerConnection connection = new ServerConnection(clientSock)) {
                String receivedRequest = connection.getInput();
                Request request = new Request(receivedRequest, connection);
                CommandController controller = new CommandController();

                switch (request.getType()) {
                    case "EXIT":
                        controller.setCommand(new  ExitCommand());
                        break;
                    case "PUT":
                        controller.setCommand(new PutCommand());
                        break;
                    case "DELETE":
                        controller.setCommand(new DeleteCommand());
                        break;
                    case "GET":
                        controller.setCommand(new GetCommand());
                        break;
                    default:
                        System.out.println("Unknown command: " + request.getType());
                        break;
                }

                Response response = controller.execute(request);

                connection.sendMessage(response.getMessage());

                if (response.getData() != null) {
                    connection.sendFile(response.getData());
                }
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}