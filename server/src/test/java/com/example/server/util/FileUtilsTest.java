package com.example.server.util;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

public class FileUtilsTest {
    @TempDir
    File tempDir; //JUnity creates this folder automatically

    @BeforeEach
    void setUp(){
        //Before each test we point FileUtils and JsonUtils to our temporary directory
        FileUtils.setRootPath(tempDir);
        JsonUtils.setRootPath(tempDir);
    }

    @Test
    void testCreateFileSuccess(){
        String filename = "test.txt";
        byte[] content = "Hello".getBytes();
        //Try to create a file
        FileUtils result = FileUtils.createFile(filename, content);

        //Verify result object
        assertTrue(result.isSuccess());
        assertTrue(result.getId() > 0);

        File createdFile = new File(tempDir, filename);
        assertTrue(createdFile.exists());
    }

    @Test
    void testGetDataByFileName_Positive() {
        //Create file
        String fileName = "positive.txt";
        byte[] content = "Some content".getBytes();
        FileUtils.createFile(fileName, content);

        //Read it back
        FileUtils result = FileUtils.getDataByFileName(fileName);

        //Verity
        assertTrue(result.isSuccess());
        assertArrayEquals(content, result.getData(), "Content mismatch");
    }

    @Test
    void testGetDataByFileName_Negative() {
        //Try to read non exitsting file
        FileUtils result = FileUtils.getDataByFileName("ghost.txt");
        assertFalse(result.isSuccess(), "Should fail for missing file");
    }

    @Test
    void testDeleteFile() {
        String filename = "delete_me.txt";
        FileUtils.createFile(filename, new byte[0]);

        //ensuring it exists first
        assertTrue(new File(tempDir, filename).exists());

        //delete it
        FileUtils result = FileUtils.deleteFileByName(filename);

        assertTrue(result.isSuccess());
        assertFalse(new File(tempDir, filename).exists());
    }

    @Test
    void testGetDataByFileName() {
        FileUtils result = FileUtils.getDataByFileName("ghost.txt");
        assertFalse(result.isSuccess());
    }
}
