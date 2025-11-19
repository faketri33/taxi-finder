package org.faketri.domain.mapper;

import dto.ride.RideResponseDto;
import org.faketri.domain.entity.ride.model.Ride;

public class RideMapper {
    private RideMapper(){}
    public static RideResponseDto toDto(Ride e){
        return new RideResponseDto(
                e.getId(),
                e.getStatus(),
                e.getStartAddress() != null ? AddressMapper.toDto(e.getStartAddress()) : null,
                e.getEndAddress() != null ? AddressMapper.toDto(e.getEndAddress()) : null,
                e.getCreateAt(),
                e.getCancelAt()
        );
    }
}
