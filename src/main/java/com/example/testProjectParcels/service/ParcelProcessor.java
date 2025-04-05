package com.example.testProjectParcels.service;

import com.example.testProjectParcels.model.InputData;
import com.google.gson.*;
import lombok.Setter;
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
@Setter
@Service
public class ParcelProcessor {

    private static final Logger logger = LoggerFactory.getLogger(ParcelProcessor.class);

    /*For testing purposes only*/
    @Autowired
    private JsonService jsonService;

    /**
     * Processes a parcel by adding it to the source and organized files.
     */
    public boolean process(InputData parcel) {
        if (!parcel.isValid()) {
            return false;
        }

        boolean addedToSource = jsonService.addParcelToSourceFile(parcel);
        if (!addedToSource) {
            return false;
        }

        return jsonService.addParcelToPostcodeFile(parcel);
    }


    /**
     * Gets the count of parcels in the source file.
     * @return Map with postcode as key and parcel count as value
     */
    public Map<String, Integer> getParcelCount() {
        File file = new File(jsonService.getSourceFileName());
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
