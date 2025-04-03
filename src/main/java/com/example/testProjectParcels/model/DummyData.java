package com.example.testProjectParcels.model;

public class DummyData {

    public InputData getUserData() {
        Address address = new Address("Cecilia", "Kragujewska", "10A", "Zilina", "01001");
        return new InputData("13344ABC", address);
    }
}
