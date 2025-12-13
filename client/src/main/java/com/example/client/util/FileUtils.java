package com.example.client.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class FileUtils {

    private static final String DATA_FOLDER = "client" + File.separator + "data";
    private static final File fileDir = new File(DATA_FOLDER);

    static {
        if (!fileDir.exists()) {
            boolean created = fileDir.mkdirs();
            if (created) {
                System.out.println("Created client data directory: " + fileDir.getAbsolutePath());
            }
        }
    }

    public static byte[] getFile(String fileName) {
        try {
            File filePath = new File(fileDir, fileName);
            if (!filePath.exists()) {
                System.out.println("File not found: " + fileName);
                return new byte[0];
            }
            return Files.readAllBytes(filePath.toPath());
        } catch (IOException e) {
            e.printStackTrace();
            return new byte[0];
        }
    }

    public static void writeFile(String fileName, byte[] content) {
        try {
            File filePath = new File(fileDir, fileName);
            Files.write(filePath.toPath(), content);
            System.out.println("File saved to: " + filePath.getAbsolutePath());
        } catch (IOException e) {
            System.out.println("Cannot write file: " + fileName);
            e.printStackTrace();
        }
    }
}
