package org.faketri.usecase.mapper;

import org.faketri.domain.entity.DispatchState;
import org.faketri.infrastructure.persistence.entity.DispatchStateEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DispatchStateMapper {

    @Mapping(target = "rideId", source = "rideId")
    DispatchStateEntity toEntity(DispatchState e);
    @Mapping(target = "rideId", source = "rideId")
    DispatchState toDomain(DispatchStateEntity e);
}
