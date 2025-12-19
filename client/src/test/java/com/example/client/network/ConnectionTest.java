package com.example.client.network;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.*;

class ConnectionTest {

    private ServerSocket fakeServer;
    private ExecutorService executor;
    private static final int PORT = 23456;

    @BeforeEach
    void setUp() throws IOException {
        fakeServer = new ServerSocket(PORT);
        executor = Executors.newSingleThreadExecutor();
    }

    @AfterEach
    void tearDown() throws IOException {
        fakeServer.close();
        executor.shutdownNow();
    }

    @Test
    void testSendMessage() throws IOException {
        executor.submit(() -> {
            try (Socket clientSocket = fakeServer.accept();
                 DataInputStream in = new DataInputStream(clientSocket.getInputStream())) {

                String received = in.readUTF();
                assertEquals("HELLO SERVER", received);

            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        try (ClientConnection connection = ClientConnection.startClient()) {
            connection.sendMessage("HELLO SERVER");
        }
    }

    @Test
    void testSendFile() throws IOException {
        File tempFile = File.createTempFile("test_upload", ".txt");
        try (FileWriter writer = new FileWriter(tempFile)) {
            writer.write("Hello World Data");
        }

        executor.submit(() -> {
            try (Socket clientSocket = fakeServer.accept();
                 DataInputStream in = new DataInputStream(clientSocket.getInputStream())) {

                int length = in.readInt();
                assertEquals(16, length);

                byte[] buffer = new byte[length];
                in.readFully(buffer);
                assertEquals("Hello World Data", new String(buffer));

            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        try (ClientConnection connection = ClientConnection.startClient()) {
            connection.sendFile(tempFile);
        }

        tempFile.delete();
    }
}