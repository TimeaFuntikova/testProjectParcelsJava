package com.example.testProjectParcels.service;

import com.example.testProjectParcels.enums.Messages;
import com.example.testProjectParcels.model.InputData;
import com.google.gson.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.*;
import java.util.Collections;
import java.util.Map;

/**
 * Service class for processing parcels.
 * This class handles the logic for adding parcels to source and postcode files.
 */
@Service
public class ParcelProcessor {

    private static final Logger logger = LoggerFactory.getLogger(ParcelProcessor.class);

    @Autowired
    private JsonService jsonService;

    /**
     * Processes the given parcel by adding it to the source and postcode files.
     * @param parcel The parcel to process
     * @return true if the parcel was added successfully, false otherwise
     */
    public boolean process(InputData parcel) {
        if (!parcel.isValid()) {
            return false;
        }

        boolean addedToSource = jsonService.addParcelToSourceFile(parcel);
        if (addedToSource) {
            jsonService.addParcelToPostcodeFile(parcel);
        }

        return addedToSource;
    }

    /**
     * Gets the count of parcels in the source file.
     * @return Map with postcode as key and parcel count as value
     */
    public Map<String, Integer> getParcelCount() {
        File file = new File(Messages.FILE_NAME_SOURCE.getMessage());
        if (!file.exists()) return Collections.singletonMap("count", 0);

        try (Reader reader = new FileReader(file)) {
            JsonObject json = JsonParser.parseReader(reader).getAsJsonObject();
            JsonArray parcels = json.getAsJsonArray("parcels");
            int count = (parcels != null) ? parcels.size() : 0;
            return Collections.singletonMap("count", count);
        } catch (Exception e) {
            logger.error("Failed to read parcel count", e);
            return Collections.singletonMap("count", 0);
        }
    }
}
