package com.example.testProjectParcels.service;

import com.example.testProjectParcels.model.Address;
import com.example.testProjectParcels.model.InputData;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.io.TempDir;
import java.io.File;
import java.nio.file.Path;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for JsonService class.
 */
class JsonServiceTest {

    private JsonService jsonService;
    private InputData testParcel;

    /**
     * Set up the test environment before each test.
     */
    @BeforeEach
    void setUp() {
        jsonService = new JsonService("parcels_source_test.json");
        testParcel = new InputData("test-duplicate-123", new Address("Test", "Main", "1", "Town", "99999"));

        File source = new File("parcels_source_test.json");
        File organized = new File("parcels_99999.json");
        if (source.exists()) source.delete();
        if (organized.exists()) organized.delete();
    }

    @Test
    void shouldWriteToTempFile(@TempDir Path tempDir) {
        File testFile = tempDir.resolve("parcels_source_test.json").toFile();
        JsonService service = new JsonService(testFile.getPath());

        System.out.println("Created? " + new File("parcels_source_test.json").exists());

        boolean result = service.addParcelToSourceFile(testParcel);
        assertTrue(result);
    }

    /**
     * Clean up the test files after each test.
     */
    @AfterEach
    void cleanUp() {
        new File("parcels_source_test.json").delete();
        new File("parcels_99999.json").delete();
    }

    /**
     * Test to ensure that a single parcel can be added to the source file.
     */
    @Test
    void shouldAddToSourceFile(@TempDir Path tempDir) {
        File testFile = tempDir.resolve("parcels_source_test.json").toFile();
        JsonService service = new JsonService(testFile.getPath());

        boolean result = service.addParcelToSourceFile(testParcel);
        assertTrue(result);
        assertTrue(testFile.exists());
    }

    /**
     * Test to ensure that duplicate parcels are not added to the postcode file.
     */
    @Test
    void shouldAddToPostcodeFileOnce() {
        boolean first = jsonService.addParcelToPostcodeFile(testParcel);
        boolean second = jsonService.addParcelToPostcodeFile(testParcel);

        assertTrue(first, "First insert should succeed");
        assertFalse(second, "Second insert (duplicate) should fail");
    }

    /**
     * Test to ensure that the duplicate parcel is not added to the source file.
     */
    @Test
    void shouldRejectDuplicateInSourceFile() {
        assertTrue(jsonService.addParcelToSourceFile(testParcel));
        assertFalse(jsonService.addParcelToSourceFile(testParcel));
    }
}

