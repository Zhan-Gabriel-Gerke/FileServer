package com.example.client.util;

import java.io.File;
import java.nio.file.Files;

public class FileUtils {

    private static final File fileDir = new File("C:\\Users\\zange\\IdeaProjects\\FileServer\\client\\src\\main\\java\\com\\example\\client\\data");

    public static byte[] getFile(String fileName) {
        try {
            File filePath = new File(fileDir, fileName);
            return Files.readAllBytes(filePath.toPath());
        } catch (Exception e) {
            System.out.println("File not found: " + fileName);
            return new byte[0];
        }
    }

    public static void writeFile(String fileName, byte[] content) {
        try {
            File filePath = new File(fileDir, fileName);
            Files.write(filePath.toPath(), content);
        } catch (Exception e) {
            System.out.println("Cannot write file: " + fileName);
        }
    }
}
