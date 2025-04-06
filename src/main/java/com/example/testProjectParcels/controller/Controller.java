package com.example.testProjectParcels.controller;

import com.example.testProjectParcels.enums.Messages;
import com.example.testProjectParcels.model.InputData;
import com.example.testProjectParcels.model.dto.ParcelDTO;
import com.example.testProjectParcels.model.mapper.ParcelMapper;
import com.example.testProjectParcels.service.ParcelProcessor;
import jakarta.validation.Valid;
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
     * Endpoint for adding a new parcel.
     * @param dto Parcel data transfer object
     * @return ResponseEntity with success or error message
     */
    @PostMapping("/add")
    public ResponseEntity<String> addParcel(@Valid @RequestBody ParcelDTO dto) {
        InputData input = ParcelMapper.fromDto(dto);

        return processor.process(input)
                ? ResponseEntity.ok(Messages.ADDED_SUCCESSFULLY.get())
                : ResponseEntity.badRequest().body(Messages.INVALID_DATA.get() + " or " + Messages.MISSING_DATA.get());
    }
}
