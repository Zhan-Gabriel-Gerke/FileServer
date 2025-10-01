package com.example.client.network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
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

    public void sendFile(byte[] fileData) throws IOException {
        output.writeInt(fileData.length);
        output.write(fileData);
        output.flush();
    }

    public byte[] getFile() throws IOException {
        int length = input.readInt();
        byte[] fileData = new byte[length];
        input.readFully(fileData);
        return fileData;
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
