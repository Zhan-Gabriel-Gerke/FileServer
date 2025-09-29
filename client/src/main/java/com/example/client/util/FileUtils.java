package com.example.client.util;

import java.io.File;
import java.nio.file.Files;

public class FileUtils {
    private static final File file = new File("C:\\Users\\zange\\IdeaProjects\\FileServer\\client\\src\\main\\java\\com\\example\\client\\data");


    public static byte[] getFile(String fileName) {
        try{
            File filePath = new File(file, fileName);
            return Files.readAllBytes(filePath.toPath());
        } catch(Exception e){
            System.out.println("File not found");
            return new byte[0];
        }
    }

    public static void writeFile(String fileName, byte[] fileContent) {
        try{
            Files.write(file.toPath(), fileContent);
        } catch(Exception ignored){
        }
    }
}
