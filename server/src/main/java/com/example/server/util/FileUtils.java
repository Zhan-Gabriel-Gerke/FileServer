package com.example.server.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class FileUtils {


    private static final String DATA_FOLDER = "server" + File.separator + "data";
    private static  File path = new File(DATA_FOLDER);

    static {
        if (!path.exists()) {
            boolean created = path.mkdir();
            if (!created) {
                System.out.println("Created data directory: " + path.getAbsolutePath());
            }
        }
    }

    private final boolean success;
    private final int id;
    private final byte[] data;
    private final File file;

    public FileUtils(boolean success, int id) {
        this.success = success;
        this.id = id;
        this.data = new byte[0];
        this.file = null;
    }

    public FileUtils(boolean success, byte[] data) {
        this.success = success;
        this.data = data;
        this.id = 0;
        this.file = null;
    }

    public FileUtils(boolean success) {
        this.success = success;
        this.data = new byte[0];
        this.id = 0;
        this.file = null;
    }

    public FileUtils(boolean success, File file) {
        this.success = success;
        this.file = file;
        this.data = new byte[0];
        this.id = 0;
    }

    //Method for Unit Testing to injection a temporary folder
    public static void setRootPath(File newPath){
        path = newPath;
    }

    public boolean isSuccess() {
        return success;
    }
    public int getId() {
        return id;
    }

    public byte[] getData() {
        return data;
    }

    public File getFile() {
        return file;
    }

    public static FileUtils deleteFileByName(String fileName){
        try {
            File file = new File(path, fileName);
            return deleteFileByFile(file);
        } catch (SecurityException e) {
            System.err.println("Delete attempt rejected: " + e.getMessage());
            return new FileUtils(false);
        }
    }

    public static FileUtils deleteFileByFile(File file){
        if (file.exists()){
            if (file.delete()){
                return new FileUtils(true,  file);
            }
        }
        return new FileUtils(false);
    }

    public static FileUtils getDataByFileName(String fileName){
        try {
            File file = new File(path, fileName);
            return getDataByFile(file);
        } catch (SecurityException e){
            System.err.println("Read attempt rejected: " + e.getMessage());
            return new FileUtils(false);
        }
    }

    public static FileUtils getDataByFile(File file){
        if (file.exists() && file.isFile()){
            try{
                byte[] data = Files.readAllBytes(file.toPath());
                return new FileUtils(true, data);
            } catch (IOException e) {
                e.printStackTrace();
                return new FileUtils(false);
            }
        }
        return new FileUtils(false);
    }

    public static FileUtils createFile(String fileName, byte[] data){
        try {
            File file = getFileSafe(fileName);
            if (!file.exists() && file.createNewFile()) {
                Files.write(file.toPath(), data);
                int id = JsonUtils.addRecord(file);
                return new FileUtils(true, id);
            } else {
                return new FileUtils(false);
            }
        } catch (IOException | SecurityException e) {
            e.printStackTrace();
            return new FileUtils(false);
        }
    }

    public static File getFileSafe(String fileName) throws IOException {
        File root = path.getCanonicalFile();
        File file = new File(root, fileName).getCanonicalFile();

        if (!file.getPath().startsWith(root.getPath())) {
            throw new SecurityException("Access denied: path traversal attempt detected.");
        }
        return file;
    }

}
