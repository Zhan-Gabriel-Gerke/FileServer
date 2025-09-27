package com.example.server.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

public class JsonUtils {

    private static Map<Integer, File> treeMap = new TreeMap<>();
    private static final File mapFile = new File("C:\\Users\\zange\\IdeaProjects\\FileServer\\server\\src\\main\\java\\com\\example\\server\\AppData\\map.json");


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
        for (Map.Entry<Integer, File> entry : treeMap.entrySet()) {
            if (entry.getValue().equals(file)){
                treeMap.remove(entry.getKey());
            }
        }
    }

    private static void deleteRecordByKey(int id){
        treeMap.remove(id);
        saveTheMap();
    }


    private static int addRecordToMap(File file){
        Map.Entry<Integer, File> lastEntry = ((TreeMap<Integer, File>) treeMap).lastEntry();
        if (lastEntry == null){
            treeMap.put(1, file);
            return 1;
        }
        int id = lastEntry.getKey() + 1;
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
        } catch (IOException ignored){
        }
    }

    private static void takeMap(){
        ObjectMapper mapper = new ObjectMapper();
        if (!mapFile.exists() || mapFile.length() == 0){
            if (treeMap.isEmpty()){
                treeMap = new TreeMap<>();
            }
        }
        try{
            Map<Integer, String> tempMap = mapper.readValue(mapFile, new TypeReference<>() {
            });
            Map<Integer, File> resultMap = new TreeMap<>();
            for (Map.Entry<Integer, String> entry : tempMap.entrySet()){
                resultMap.put(entry.getKey(), new File(entry.getValue()));
            }
            treeMap = resultMap;
        } catch (IOException e){
            if (treeMap.isEmpty()){
                treeMap = new TreeMap<>();
            }
        }
    }
}
