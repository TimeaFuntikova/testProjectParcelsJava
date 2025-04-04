package com.example.testProjectParcels.controller;

import com.example.testProjectParcels.service.ParcelProcessor;

/**
 * Controller class that handles the interaction between the user and the service layer.
 * It is responsible for processing user input and managing the flow of data.
 */
public class Controller {
    private final ParcelProcessor processor = new ParcelProcessor();

    public Controller() {
        // Default constructor intentionally left empty
    }

    public void processFromController() {
        processor.process();
    }

}
