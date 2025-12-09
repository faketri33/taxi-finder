package org.faketri.api.dto.mapper;

import dto.ride.RideResponseDto;
import org.faketri.api.dto.request.RideRequestDto;
import org.faketri.domain.model.ride.Ride;
import org.faketri.infrastructure.persistence.entity.ride.model.RideEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RideMapper {

    Ride toDomain(RideEntity entity);

    RideEntity toEntity(Ride domain);

    RideResponseDto toResponse(Ride domain);

    Ride toDomain(RideRequestDto dto);
}