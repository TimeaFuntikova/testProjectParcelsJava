package com.example.testProjectParcels.enums;

public enum Messages {
    FILE_NAME_SOURCE("parcels_source.json"),
    FILE_NAME_ORGANIZED("parcels_"),
    RECORD_NOT_ADDED("Record not added"),
    RECORD_EXISTS_IN_FILE("Record already exists in file "),
    FILE_CREATED("File created successfully "),
    FILE_UPDATED("File updated successfully ");

    private final String message;

    Messages(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
