package com.example.testProjectParcels.model.dto;

import lombok.Data;

@Data
public class ParcelDTO {
    private String parcelId;
    private Address address;

    @Data
    public static class Address {
        private String name;
        private String street;
        private String number;
        private String city;
        private String postcode;

        public Address(String name) {
            this.name = name;
        }

        public Address() {}
    }
}
