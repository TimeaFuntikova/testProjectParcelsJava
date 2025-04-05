package com.example.testProjectParcels.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InputData {
    private String parcelId;
    private Address address;

    public boolean isValid() {
        return parcelId != null &&
                address != null &&
                address.getName() != null &&
                address.getStreet() != null &&
                address.getNumber() != null &&
                address.getCity() != null &&
                address.getPostcode() != null;
    }
}
