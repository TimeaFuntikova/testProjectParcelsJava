package com.example.testProjectParcels.model;

public class InputData {
    private String parcelId;
    private Address address;

    public InputData(String parcelId, Address address) {
        this.parcelId = parcelId;
        this.address = address;
    }

    public String getId() {
        return parcelId;
    }

    public Address getAddress() {
        return address;
    }

}