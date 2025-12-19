package com.example.server.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class RequestTest {

    @Test
    void testParseGetRequest(){
        //Simulate client request: "GET BY_ID 123"
        String input = "GET BY_ID 123";

        //Create Request object
        Request request = new Request(input, null);

        //Verify server parsed the string correctly
        assertEquals("GET", request.getType());
        assertEquals("BY_ID", request.getFirstArg());
        assertEquals("123", request.getSecondArg());
    }

    @Test
    void testParsePutRequestWithFileName() {
        String input = "PUT FILE_NAME cat.jpg";
        //Simulate file bytes
        byte[] data = {1, 2, 3, 4, 5};

        Request request = new Request(input, null);

        assertEquals("PUT", request.getType());
        assertEquals("FILE_NAME", request.getFirstArg());
        assertEquals("cat.jpg", request.getSecondArg());
    }

    @Test
    void testExitRequest() {
        String input = "EXIT";
        Request request = new Request(input, null);

        assertEquals("EXIT", request.getType());
        //Exit command has no arguments
        assertNull(request.getFirstArg());
        assertNull(request.getSecondArg());
    }
}
