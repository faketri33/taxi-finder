package org.faketri.api.dto.mapper;

import dto.address.AddressResponseDto;
import org.faketri.api.dto.request.AddressRequestDto;
import org.faketri.domain.model.address.Address;
import org.faketri.infrastructure.persistence.entity.address.model.AddressEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AddressMapper {
    AddressResponseDto toResponseDto(Address domain);
    Address toDomain(AddressRequestDto dto);
    Address toDomain(AddressEntity domain);
    AddressEntity toEntity(Address address);
}
