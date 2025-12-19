package com.example.client.network;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class ClientConnection implements AutoCloseable {
    private final DataInputStream input;
    private final DataOutputStream output;
    private final Socket socket;

    public ClientConnection(Socket socket) throws IOException {
        this.socket = socket;
        this.input = new DataInputStream(socket.getInputStream());
        this.output = new DataOutputStream(socket.getOutputStream());
    }

    public void sendMessage(String message) throws IOException {
        output.writeUTF(message);
        output.flush();
    }

    public String getMessage() throws IOException {
        return input.readUTF();
    }

    public void sendFile(File fileData) throws IOException {
        output.writeInt((int) fileData.length());
        try (FileInputStream fileInput = new FileInputStream(fileData)){
            byte[] buffer = new byte[8192];
            int read;
            while ((read = fileInput.read(buffer)) != -1){
                output.write(buffer, 0, read);
            }
        }
        output.flush();
    }

    public void getFile(File targetFile) throws IOException {
        int length = input.readInt();

        try(FileOutputStream fileOutput = new FileOutputStream(targetFile)) {
            byte[] buffer = new byte[8192];
            int totalRead = 0;
            while (totalRead < length) {
                int bytesToRead = Math.min(buffer.length, length - totalRead);
                int read = input.read(buffer, 0, bytesToRead);
                if (read == -1) throw new IOException("Connection lost");

                fileOutput.write(buffer, 0, read);
                totalRead += read;
            }
            fileOutput.flush();
        }
    }

    public static ClientConnection startClient() throws IOException {
        String address = "127.0.0.1";
        int port = 23456;
        Socket socket = new Socket(InetAddress.getByName(address), port);
        return new ClientConnection(socket);
    }

    @Override
    public void close() throws IOException {
        input.close();
        output.close();
        socket.close();
    }
}
