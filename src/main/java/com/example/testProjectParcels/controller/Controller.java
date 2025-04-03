package com.example.testProjectParcels.controller;

import com.example.testProjectParcels.model.DummyData;
import com.example.testProjectParcels.model.InputData;
import com.example.testProjectParcels.service.JsonService;
import com.example.testProjectParcels.enums.Messages;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Controller class that handles the interaction between the user and the service layer.
 * It is responsible for processing user input and managing the flow of data.
 */
public class Controller {
    private final JsonService jsonService = new JsonService();
    private final DummyData dummyData = new DummyData();
    private static final Logger logger = LoggerFactory.getLogger(Controller.class);

    public Controller() {
        // Default constructor intentionally left empty
    }

    public void addRecord() {
       InputData data = dummyData.getUserData();
       if (jsonService.addParcelToFile(data)) {
           jsonService.addParcelToPostcodeFile(data);
       } else {
           logger.info(Messages.RECORD_NOT_ADDED.getMessage());
       }
    }
}
