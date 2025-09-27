package com.example.server.util;

import java.io.File;

public class FileUtils {
    private static final File path = new File("C:\\Users\\zange\\IdeaProjects\\File Server\\File Server\\task\\src\\server\\data");

    public static File getPath() {
        return path;
    }

    public static String deleteFileByName(String name){
        File file = new File(path,name);
        if(file.exists()){
            if (file.delete()){
                return "200";
            }
        }
        return "404";
    }

    public static String deleteFileByFile(File file){
        if (file.exists()){
            if (file.delete()){
                return "200";
            }
        }
        return "404";
    }

}
