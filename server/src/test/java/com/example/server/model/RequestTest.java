package com.example.server.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class RequestTest {

    @Test
    void testParseGetRequest(){
        //Simulate client request: "GET BY_ID 123"
        String input = "GET BY_ID 123";
        byte[] emptyData = new byte[0];

        //Create Request object
        Request request = new Request(input, emptyData);

        //Verify server parsed the string correctly
        assertEquals("GET", request.getType());
        assertEquals("BY_ID", request.getFirsArg());
        assertEquals("123", request.getSecondArg());
    }

    @Test
    void testParsePutRequestWithFileName() {
        String input = "PUT FILE_NAME cat.jpg";
        //Simulate file bytes
        byte[] data = {1, 2, 3, 4, 5};

        Request request = new Request(input, data);

        assertEquals("PUT", request.getType());
        assertEquals("FILE_NAME", request.getFirsArg());
        assertEquals("cat.jpg", request.getSecondArg());
        assertArrayEquals(data, request.getData());
    }

    @Test
    void testNormalizeSlash(){
        //Verify path normalization
        //Client might send path with double slashes
        String input = "PUT FILE_NAME my_folder\\\\file.txt";
        Request request = new Request(input, new  byte[0]);

        //Call normalization method
        request.normalizeSlash();

        //Expect path to be normalized
        assertEquals("my_folder\\\\file.txt", request.getSecondArg());
    }

    @Test
    void testExitRequest() {
        String input = "EXIT";
        Request request = new Request(input, new byte[0]);

        assertEquals("EXIT", request.getType());
        //Exit command has no arguments
        assertNull(request.getFirsArg());
        assertNull(request.getSecondArg());
    }
}
