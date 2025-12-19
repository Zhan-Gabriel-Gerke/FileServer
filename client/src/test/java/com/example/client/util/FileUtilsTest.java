package com.example.client.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class FileUtilsTest {

    @Test
    void testGetSafeFileObject_ValidPath() throws IOException {
        String fileName = "cat.jpg";
        File file = FileUtils.getSafeObject(fileName);
        assertTrue(file.getAbsolutePath().contains("data" + File.separator + "cat.jpg"));
    }

    @Test
    void testGetSafeFileObject_PathTraversalAttempt() {
        String hackPath = "../../../Windows/System32/drivers/etc/hosts";

        assertThrows(SecurityException.class, () -> {
            FileUtils.getSafeObject(hackPath);
        }, "Should throw exception for path traversal attempts");
    }

    @Test
    void testGetSafeFileObject_RootTraversalAttempt() {
        String hackPath = "/etc/passwd";

        try {
            File result = FileUtils.getSafeObject(hackPath);
            assertTrue(result.getAbsolutePath().contains("client" + File.separator + "data"));
        } catch (Exception e) {
            assertTrue(e instanceof SecurityException || e instanceof IOException);
        }
    }
}