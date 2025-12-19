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

    public static File getSafeObject(String fileName) throws IOException{
        File file = new File(fileDir, fileName).getCanonicalFile();
        if (!file.getPath().startsWith(fileDir.getCanonicalPath())){
            throw new SecurityException("Security Error: Path traversal attempt!");
        }
        return file;
    }
}
