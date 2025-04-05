package com.example.testProjectParcels.controller;

import com.example.testProjectParcels.model.InputData;
import com.example.testProjectParcels.service.ParcelProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

/**
 * Controller class for handling parcel-related requests.
 */
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/parcels")
public class Controller {

    @Autowired
    private ParcelProcessor processor = new ParcelProcessor();

    /**
     * Endpoint for getting the count of parcels in the postcode file.
     * @return Map with postcode as key and parcel count as value
     */
    @GetMapping("/count")
    public Map<String, Integer> getParcelCount() {
        return processor.getParcelCount();
    }

    /**
     * Endpoint for adding a single parcel.
     * @param data Parcel to add
     * @return ResponseEntity with status and message
     */
    @PostMapping("/add")
    public ResponseEntity<String> addParcel(@RequestBody InputData data) {
        if (data.getParcelId() == null || data.getAddress() == null || data.getAddress().getPostcode() == null) {
            return ResponseEntity.badRequest().body("❌ Invalid input data.");
        }

        if (!data.isValid()) {
            return ResponseEntity.badRequest().body("⚠️ Missing required parcel fields.");
        }

        processor.process(data);
        return ResponseEntity.ok("✅ Parcel added successfully.");
    }
}
