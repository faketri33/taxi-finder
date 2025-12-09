package org.faketri.domain.repository;

import org.faketri.infrastructure.persistence.entity.ride.model.RideEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface RideRepository {

    Optional<RideEntity> findById(UUID uuid);

    Page<RideEntity> findByUserId(UUID uuid, Pageable pageable);
    int acceptRide(UUID rideId, UUID driverId);

    RideEntity save(RideEntity e);
}
