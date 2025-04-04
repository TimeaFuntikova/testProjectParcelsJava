package com.example.testProjectParcels;

import com.example.testProjectParcels.controller.Controller;

public class ProcessorApplication {
    public static void main(String[] args) {
       Controller controller = new Controller();
       controller.processFromController();
    }
}