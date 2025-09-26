package com.example.server;

import com.example.server.network.ServerConnection;
import com.example.server.util.JsonUtils;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

public class MainServer {
    private static final Map<Integer, File> treeMap = JsonUtils.getMap();
    private static ServerSocket server;
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
        } catch (IOException e) {
            if (!ServerConnection.isServerRunning()) {
                //System.out.println("Server stopped!");
            }
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
            String respond = "";
            switch (receivedRequest.split("\\s+")[0]) {
                case "EXIT":
                    JsonUtils.save();
                    connection.sendMessage("200 Server shutting down");
                    ServerConnection.stopServer();
                    server.close();
                    connection.close();
                    return;
                case "PUT":
                    //add
                    byte[] file = connection.getFile();
                    respond = createFile(receivedRequest, file);
                    break;
                case "DELETE":
                    //delete
                    respond = deleteFile(receivedRequest);
                    break;
                case "GET":
                    //get
                    fileBytes = getFile(receivedRequest);
                    break;
            }
            if (receivedRequest.split("\\s+")[0].equals("GET")) {
                if (fileBytes != null) {
                    connection.sendMessage("200");//OK
                    connection.sendFile(fileBytes);
                }
            } else {
                connection.sendMessage("404");//ERROR
            }
            connection.close();
        }
    }
    private static String createFile(String receivedRequest, byte[] fileData) {
        String[] commands = receivedRequest.split(" ", 3);
        File path = new File("C:\\Users\\zange\\IdeaProjects\\File Server\\File Server\\task\\src\\server\\data");
        File file = new File(path,commands[2]);
        try {
            if (!file.exists() && file.createNewFile()) {
                Files.write(Paths.get(file.getPath()), fileData);
                int id = JsonUtils.addRecord(file);
                return "200" + " Id " + id;//OK
            } else {
                return "403";//Error
            }
        } catch (IOException e) {
            return "403";//Error
        }
    }

    private static String deleteFile(String receivedRequest) {
        String[] commands = receivedRequest.split(" ", 3);
        File file;
        if (commands[1].equals("BY_NAME")){
            File path = new File("C:\\Users\\zange\\IdeaProjects\\File Server\\File Server\\task\\src\\server\\data");
            file = new File(path,commands[2]);
        } else {
            file = JsonUtils.getFile(Integer.parseInt(commands[2]));
        }

        try {
            if (file.delete()){
                return "200";//OK
            } else {
                return "404";//Error
            }
        } catch (Exception e) {
            return "404";//Error
        }
    }

    private static byte[] getFile(String receivedRequest) {
        String[] commands = receivedRequest.split(" ", 3);
        File file;
        if (commands[1].equals("BY_NAME")){
            if (commands[2].contains("\\"))
            {
                int index = commands[2].lastIndexOf("\\");
                commands[2] = commands[2].substring(0, index) + "\\" + commands[2].substring(index + 1);
            }
            File path = new File("C:\\Users\\zange\\IdeaProjects\\File Server\\File Server\\task\\src\\server\\data");
            file = new File(path,commands[2]);
        } else {
            file = JsonUtils.getFile(Integer.parseInt(commands[2]));
        }
        try {
            if (file.exists() && file.isFile()) {
                //here all
                return Files.readAllBytes(file.toPath());
            }
        } catch (Exception ignored){
        }
        return null;
    }
}