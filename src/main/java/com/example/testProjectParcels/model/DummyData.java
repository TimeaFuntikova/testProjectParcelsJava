package com.example.testProjectParcels.model;

import java.util.List;

public class DummyData {

    public List<InputData> getSampleParcels() {
        return List.of(
                new InputData("P001", new Address("John Doe", "Main Street", "12", "Bratislava", "81101")),
                new InputData("P002", new Address("Alice Smith", "Dunajská", "8", "Bratislava", "81101")),
                new InputData("P003", new Address("Bob Marley", "Hlavná", "5", "Košice", "04001")),
                new InputData("P004", new Address("Jane Roe", "Ružinovská", "21B", "Bratislava", "82102")),
                new InputData("P005", new Address("Mike Doe", "Komenského", "3", "Prešov", "08001"))
        );
    }
}
