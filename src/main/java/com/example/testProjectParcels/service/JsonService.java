package com.example.testProjectParcels.service;

import com.example.testProjectParcels.enums.Messages;
import com.example.testProjectParcels.model.InputData;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.io.*;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

/**
 * Service class for handling JSON file operations related to parcels.
 */
@Getter
@Service
public class JsonService {
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private static final Logger logger = LoggerFactory.getLogger(JsonService.class);

    private final String sourceFileName;
    private final String organizedPrefix;

    public JsonService() {
        this.sourceFileName = Messages.FILE_NAME_SOURCE.getMessage();
        this.organizedPrefix = Messages.FILE_NAME_ORGANIZED.getMessage();
    }

    /**
     * Constructor for JsonService with a custom source file name - for testing purposes.
     * @param sourceFileName Custom source file name
     */
    public JsonService(String sourceFileName) {
        this.sourceFileName = sourceFileName;
        this.organizedPrefix = Messages.FILE_NAME_ORGANIZED.getMessage();
    }

    /**
     * Adds a parcel to the source file.
     * @param inputData Parcel data to add
     * @return true if added successfully, false otherwise
     */
    public boolean addParcelToSourceFile(InputData inputData) {
        File sourceFile = new File(sourceFileName);
        List<InputData> existingParcels = new ArrayList<>();

        try {
            if (sourceFile.exists() && sourceFile.length() > 0) {
                String content = new String(Files.readAllBytes(sourceFile.toPath()), StandardCharsets.UTF_8);
                JsonObject root = JsonParser.parseString(content).getAsJsonObject();
                JsonArray array = root.getAsJsonArray("parcels");

                Type listType = new TypeToken<List<InputData>>() {}.getType();
                existingParcels = gson.fromJson(array, listType);

                for (InputData existing : existingParcels) {
                    if (existing.getParcelId().equals(inputData.getParcelId())) {
                        logger.warn("Parcel already exists in source file.");
                        return false;
                    }
                }
            }

            existingParcels.add(inputData);

            JsonObject root = new JsonObject();
            root.add("parcels", gson.toJsonTree(existingParcels));

            try (Writer writer = new FileWriter(sourceFile)) {
                gson.toJson(root, writer);
            }

            logger.info("✅ Parcel added to source file.");
            return true;

        } catch (IOException | JsonParseException e) {
            logger.error("❌ Failed to append to source file: {}", e.getMessage());
            return false;
        }
    }

    /**
     * Adds a parcel to the postcode file.
     * @param inputData Parcel data to add
     * @return true if added successfully, false otherwise
     */
    public boolean addParcelToPostcodeFile(InputData inputData) {
        String fileName = organizedPrefix + inputData.getAddress().getPostcode() + ".json";
        File file = new File(fileName);
        List<InputData> parcels = new ArrayList<>();

        try {
            if (file.exists() && file.length() > 0) {
                String content = new String(Files.readAllBytes(file.toPath()), StandardCharsets.UTF_8).trim();
                JsonObject root = JsonParser.parseString(content).getAsJsonObject();
                JsonArray array = root.getAsJsonArray("parcels");

                Type listType = new TypeToken<List<InputData>>() {}.getType();
                parcels = gson.fromJson(array, listType);

                for (InputData existing : parcels) {
                    if (existing.getParcelId().equals(inputData.getParcelId())) {
                        logger.warn("⚠️ Parcel already exists in postcode file '{}'", fileName);
                        return false;
                    }
                }
            }

            parcels.add(inputData);
            JsonObject wrapper = new JsonObject();
            wrapper.add("parcels", gson.toJsonTree(parcels));

            try (Writer writer = new FileWriter(file)) {
                gson.toJson(wrapper, writer);
            }

            logger.info("📦 Parcel added to {}", fileName);
            return true;

        } catch (IOException | JsonParseException e) {
            logger.error("❌ Failed to update postcode file '{}': {}", fileName, e.getMessage());
            return false;
        }
    }
}
