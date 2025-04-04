package com.example.testProjectParcels.service;

import com.example.testProjectParcels.enums.Messages;
import com.example.testProjectParcels.model.InputData;
import com.google.gson.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

/**
 * Class for creating and updating JSON file with parcel data.
 */
public class JsonService {
    private static final Logger logger = LoggerFactory.getLogger(JsonService.class);
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public void addParcelToPostcodeFile(InputData inputData) {
        String postcode = inputData.getAddress().getPostcode();
        String fileName = "parcela_" + postcode + ".json";
        addParcelToSourceFile(inputData, fileName);
    }

    /**
     * Method for checking if the parcel already exists in the JSON file.
     * @param inputData Input data containing the parcel ID
     * @param fileName Name of the JSON file
     * @return true if the parcel already exists, false otherwise
     */
    private boolean isDuplicate(InputData inputData, String fileName) {
        File file = new File(fileName);
        if (!file.exists() || file.length() == 0) {
            return false;
        }
        try {
            String content = new String(Files.readAllBytes(file.toPath()), StandardCharsets.UTF_8).trim();
            if (content.equals("[]") || content.isEmpty()) {
                return false;
            }
            JsonArray array = JsonParser.parseString(content).getAsJsonArray();
            for (JsonElement element : array) {
                JsonObject obj = element.getAsJsonObject();
                String existingParcelId = obj.get("parcelId").getAsString();
                if (existingParcelId.equals(inputData.getId())) {
                    return true;
                }
            }
        } catch (IOException | JsonSyntaxException e) {
            logger.error("An error occurred while checking for duplicates in the file: {}", fileName, e);
        }
        return false;
    }

    /**
     * Method for adding a new parcel to the JSON file.
     * @param inputData Input data containing the parcel ID and address
     * @param fileName Name of the JSON file
     * @return true if the parcel was added successfully, false otherwise
     */
    private boolean addParcelToSourceFile(InputData inputData, String fileName) {

        if (isDuplicate(inputData, fileName)) {
            logger.info("{}{}", Messages.RECORD_EXISTS_IN_FILE.getMessage(), fileName);
            return false;
        }

        String newJson = gson.toJson(inputData);
        File file = new File(fileName);

        try {
            if (!file.exists() || file.length() == 0) {
                try (FileWriter writer = new FileWriter(file)) {
                    writer.write("[\n  " + newJson + "\n]");
                }

                logger.info("{}{}", Messages.FILE_CREATED.getMessage(), fileName);
                return true;
            }

            // read-write mode
            try (RandomAccessFile raf = new RandomAccessFile(file, "rw")) {
                long length = raf.length();
                long pos = length - 1;

                // trailing whitespaces & newlines
                raf.seek(pos);
                int b = raf.read();
                while (pos > 0 && (b == ' ' || b == '\n' || b == '\r')) {
                    pos--;
                    raf.seek(pos);
                    b = raf.read();
                }

                // expecting closing bracket - ']'
                if (b != ']') {
                    throw new IOException("Not a valid JSON Field!");
                }

                // if the file is not empty, we need to read the content to determine if we need to add a comma
                raf.seek(0);
                byte[] bytes = new byte[(int) raf.length()];
                raf.readFully(bytes);
                String content = new String(bytes, "UTF-8").trim();
                boolean emptyArray = content.equals("[]");

                // we seek to the position where we want to insert the new JSON - ']'
                raf.seek(pos);
                String toInsert;
                if (!emptyArray) {
                    // if the array is not empty, we add a comma before the new JSON
                    toInsert = ",\n  " + newJson + "\n]";
                } else {
                    // if the array is empty, we add the new JSON object without a comma
                    toInsert = "\n  " + newJson + "\n]";
                }

                // deletion of closing bracket and write the new JSON
                raf.setLength(pos);
                raf.seek(pos);
                raf.writeBytes(toInsert);
            }

            logger.info("{}{}", Messages.FILE_UPDATED.getMessage(), fileName);
            return true;
        } catch (IOException e) {
            logger.error("An error occurred while checking for duplicates in the file: {}", fileName, e);
            return false;
        }
    }
}
