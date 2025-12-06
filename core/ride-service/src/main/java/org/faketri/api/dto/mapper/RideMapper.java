package org.faketri.api.dto.mapper;

import dto.ride.RideResponseDto;
import org.faketri.api.dto.request.RideRequestDto;
import org.faketri.domain.model.ride.Ride;
import org.faketri.infrastructure.persistence.entity.ride.model.RideEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface RideMapper {

    RideMapper INSTANCE = Mappers.getMapper(RideMapper.class);

    Ride toDomain(RideEntity entity);

    RideEntity toEntity(Ride domain);

    RideResponseDto toResponse(Ride domain);

    Ride toDomain(RideRequestDto dto);
}