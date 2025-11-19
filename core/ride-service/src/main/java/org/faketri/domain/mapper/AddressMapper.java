package org.faketri.domain.mapper;

import dto.address.AddressResponseDto;
import org.faketri.domain.entity.address.model.Address;

public class AddressMapper {
    private AddressMapper(){}
    public static AddressResponseDto toDto(Address e){
        return new AddressResponseDto(
                e.getLatitude(),
                e.getLongitude(),
                e.getRawAddress(),
                e.getFormattedAddress(),
                e.getCity(),
                e.getStreet(),
                e.getHouseNumber(),
                e.getCountry(),
                e.getPostalCode()
        );
    }
}
