package com.example.testProjectParcels.model;

public class Address {
        private String name;
        private String street;
        private String number;
        private String city;
        private String postcode;

    public String getPostcode() {
        return postcode;
    }

    public Address(String name, String street, String number, String city, String postcode) {
            this.name = name;
            this.street = street;
            this.number = number;
            this.city = city;
            this.postcode = postcode;
        }
    }
