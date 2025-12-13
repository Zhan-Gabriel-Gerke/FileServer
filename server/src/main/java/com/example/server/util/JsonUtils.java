package com.example.server.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

public class JsonUtils {

    private static Map<Integer, File> treeMap = new TreeMap<>();
    private static final String DATA_FOLDER = "server" + File.separator + "data";
    private static final File mapFile = new File(DATA_FOLDER, "map.json");

    static {
        File dir = new File(DATA_FOLDER);
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }


    public static synchronized Map<Integer, File> getMap(){
        takeMap();
        return treeMap;
    }

    public static synchronized void save(){
        saveTheMap();
    }

    public static synchronized File getFile (int id){
        takeMap();
        return treeMap.get(id);
    }

    public static synchronized int addRecord(File file){
        int id = addRecordToMap(file);
        saveTheMap();
        return id;
    }

    public static void deleteRecordById(int id){
        deleteRecordByKey(id);
    }

    public static void deleteRecordByFile(File file){
        privateDeleteRecordByFile(file);
        saveTheMap();
    }

    private static void privateDeleteRecordByFile(File file){
        treeMap.entrySet().removeIf(entry -> entry.getValue().equals(file));
    }

    private static void deleteRecordByKey(int id){
        treeMap.remove(id);
        saveTheMap();
    }

    private static int addRecordToMap(File file){
        if (treeMap.isEmpty()) {
            treeMap.put(1, file);
            return 1;
        }
        // Иначе берем последний ключ + 1
        Integer lastKey = ((TreeMap<Integer, File>) treeMap).lastKey();
        int id = lastKey + 1;
        treeMap.put(id, file);
        return id;
    }

    private static File getFileById(int id){
        return treeMap.get(id);
    }

    private static void saveTheMap(){
        ObjectMapper mapper = new ObjectMapper();
        try{
            mapper.writerWithDefaultPrettyPrinter().writeValue(mapFile, treeMap);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    private static void takeMap() {
        if (!mapFile.exists() || mapFile.length() == 0) {
            if (treeMap == null) {
                treeMap = new TreeMap<>();
            }
            return;
        }

        ObjectMapper mapper = new ObjectMapper();
        try {
            Map<String, String> tempMap = mapper.readValue(mapFile, new TypeReference<>() {});

            Map<Integer, File> resultMap = new TreeMap<>();
            for (Map.Entry<String, String> entry : tempMap.entrySet()) {
                try {
                    resultMap.put(Integer.valueOf(entry.getKey()), new File(entry.getValue()));
                } catch (NumberFormatException ex) {
                    System.err.println("Skipping invalid key in JSON: " + entry.getKey());
                }
            }
            treeMap = resultMap;
        } catch (IOException e) {
            System.err.println("Error reading DB: " + e.getMessage());
            treeMap = new TreeMap<>();
        }
    }

}
