package com.example.testProjectParcels.service;

import com.example.testProjectParcels.enums.Messages;
import com.example.testProjectParcels.model.DummyData;
import com.example.testProjectParcels.model.InputData;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParcelProcessor {
    private static final Logger logger = LoggerFactory.getLogger(ParcelProcessor.class);
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private final JsonService jsonService = new JsonService();
    private final DummyData dummyData = new DummyData();

    private void createSourceWithSampleData(File sourceFile) {
        List<InputData> sampleParcels = dummyData.getSampleParcels();

        JsonObject root = new JsonObject();
        JsonArray parcelArray = gson.toJsonTree(sampleParcels).getAsJsonArray();
        root.add("parcels", parcelArray);

        try (Writer writer = new FileWriter(sourceFile)) {
            gson.toJson(root, writer);
            logger.info("Created '{}' with sample parcel data.", Messages.FILE_NAME_SOURCE.getMessage());
        } catch (IOException e) {
            logger.error("Failed to create sample source data in '{}'", Messages.FILE_NAME_SOURCE.getMessage(), e);
        }
    }


    public void process() {
        File source = new File(Messages.FILE_NAME_SOURCE.getMessage());

        if (!source.exists()) {
            createSourceWithSampleData(source);
            return;
        }

        if (source.length() == 0) {
            logger.warn("Source file '{}' is empty. Nothing to process.", Messages.FILE_NAME_SOURCE.getMessage());
            return;
        }

        try (Reader reader = new FileReader(source)) {
            JsonObject root = JsonParser.parseReader(reader).getAsJsonObject();
            JsonArray parcelsArray = root.getAsJsonArray("parcels");

            Type listType = new TypeToken<List<InputData>>() {}.getType();
            List<InputData> allParcels = gson.fromJson(parcelsArray, listType);

            Map<String, List<InputData>> parcelsByPostcode = new HashMap<>();
            for (InputData parcel : allParcels) {
                String postcode = parcel.getAddress().getPostcode();
                parcelsByPostcode.computeIfAbsent(postcode, k -> new ArrayList<>()).add(parcel);
            }

            for (Map.Entry<String, List<InputData>> entry : parcelsByPostcode.entrySet()) {
                String postcode = entry.getKey();
                List<InputData> parcelGroup = entry.getValue();
                logger.info("Processing postcode '{}', {} parcels", postcode, parcelGroup.size());

                for (InputData parcel : parcelGroup) {
                    jsonService.addParcelToPostcodeFile(parcel);
                }
            }

            Files.move(source.toPath(),
                    Paths.get("parcel_schema_processed.json"),
                    java.nio.file.StandardCopyOption.REPLACE_EXISTING);

            logger.info("Processing complete. Source file has been renamed.");

        } catch (Exception e) {
            logger.error("Failed to process parcels from source file.", e);
        }
    }
}
