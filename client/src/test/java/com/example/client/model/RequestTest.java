package com.example.client.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class RequestTest {

    @Test
    void testBuilder_FullRequest() {
        Request request = new Request.Builder()
                .type(ActionType.PUT)
                .identifier("server_cat.jpg")
                .arg("FILE_NAME")
                .build();

        assertEquals(ActionType.PUT, request.getType());
        assertEquals("server_cat.jpg", request.getIdentifier());
        assertEquals("FILE_NAME", request.getArg());
    }

    @Test
    void testBuilder_MinimalRequest() {
        Request request = new Request.Builder()
                .type(ActionType.EXIT)
                .build();

        assertEquals(ActionType.EXIT, request.getType());
        assertNull(request.getIdentifier());
        assertNull(request.getArg());
    }
}