package com.example.testProjectParcels.model.mapper;

import com.example.testProjectParcels.model.Address;
import com.example.testProjectParcels.model.InputData;
import com.example.testProjectParcels.model.dto.ParcelDTO;
import org.springframework.stereotype.Service;

/**
 * Mapper class for converting between ParcelDTO and InputData.
 */
@Service
public class ParcelMapper {

    /**
     * Converts ParcelDTO to InputData.
     *
     * @param dto ParcelDTO object to convert
     * @return InputData object
     */
    public static InputData fromDto(ParcelDTO dto) {
        ParcelDTO.Address dtoAddress = dto.getAddress();

        Address address = new Address(
                dtoAddress.getName(),
                dtoAddress.getStreet(),
                dtoAddress.getNumber(),
                dtoAddress.getCity(),
                dtoAddress.getPostcode()
        );

        return new InputData(dto.getParcelId(), address);
    }

    /**
     * Converts InputData to ParcelDTO - for further development.
     *
     * @param data InputData object to convert
     * @return ParcelDTO object
     */
    public static ParcelDTO toDto(InputData data) {
        ParcelDTO dto = new ParcelDTO();

        ParcelDTO.Address dtoAddress = new ParcelDTO.Address();
        dtoAddress.setName(data.getAddress().getName());
        dtoAddress.setStreet(data.getAddress().getStreet());
        dtoAddress.setNumber(data.getAddress().getNumber());
        dtoAddress.setCity(data.getAddress().getCity());
        dtoAddress.setPostcode(data.getAddress().getPostcode());

        dto.setParcelId(data.getParcelId());
        dto.setAddress(dtoAddress);

        return dto;
    }
}
