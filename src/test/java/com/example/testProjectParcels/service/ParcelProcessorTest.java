package com.example.testProjectParcels.service;

import com.example.testProjectParcels.model.Address;
import com.example.testProjectParcels.model.InputData;
import org.junit.jupiter.api.*;
import java.io.File;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the ParcelProcessor class.
 */
class ParcelProcessorTest {

    private ParcelProcessor processor;
    private InputData parcel;
    private final String TEST_SOURCE_FILE = "parcels_source_test.json";

    @BeforeEach
    void setUp() {
        JsonService jsonService = new JsonService(TEST_SOURCE_FILE);
        processor = new ParcelProcessor();
        processor.setJsonService(jsonService);

        parcel = new InputData(UUID.randomUUID().toString(), new Address("User Tester", "Main", "12", "TestCity", "99999"));

        new File(TEST_SOURCE_FILE).delete();
        new File("parcels_99999.json").delete();
    }

    /**
     * Test to ensure that a single parcel can be added to the source file
     * */
    @Test
    void shouldProcessNewParcel() {
        boolean result = processor.process(parcel);
        assertTrue(result);

        File source = new File(TEST_SOURCE_FILE);
        assertTrue(source.exists());
    }
    /**
     * Test to ensure that a duplicate parcel is not added to the source file
     */
    @Test
    void shouldRejectDuplicateParcel() {
        boolean first = processor.process(parcel);
        boolean second = processor.process(parcel);

        assertTrue(first);
        assertFalse(second);
    }

    /**
     * Test to ensure that the parcel count is correct after processing
     */
    @Test
    void shouldReturnCorrectCount() {
        processor.process(parcel);
        Map<String, Integer> count = processor.getParcelCount();
        assertEquals(1, count.get("count"));
    }
}
