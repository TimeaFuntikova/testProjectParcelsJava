package com.example.testProjectParcels.enums;

public enum Messages {
    FILE_NAME_SOURCE("parcels_source.json"),
    FILE_NAME_ORGANIZED("parcels_"),
    RECORD_NOT_ADDED("Record not added"),
    RECORD_EXISTS_IN_FILE("Record already exists in file "),
    FILE_CREATED("File created successfully "),
    FILE_UPDATED("File updated successfully "),
    FAILED_TO_READ("Failed to read file"),
    COUNT("count"),
    PARCELS("parcels"),
    INVALID_DATA("Invalid input data."),
    MISSING_DATA("Missing required parcel fields."),
    ADDED_SUCCESSFULLY("Parcel added successfully."),
    JSON_FILE_TYPE(".json"),
    UNSUPPORTED_BROWSER("Unsupported browser. Please open manually: "),
    OPENING_BROWSER("Opening browser at: "),
    FAILED_TO_OPEN_BROWSER("Failed to open browser: ");

    private final String message;

    Messages(String message) {
        this.message = message;
    }

    public String get() {
        return message;
    }
}
