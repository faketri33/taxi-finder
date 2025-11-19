package org.faketri.infrastructure.ride.gateway;

import org.faketri.domain.entity.ride.model.Ride;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface RideService {

    Optional<Ride> findById(UUID uuid);
    Page<Ride> findByUserId(UUID uuid, Pageable pageable);
    Ride save(Ride e);
}
