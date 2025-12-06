package org.faketri.domain.repository;

import org.faketri.domain.model.ride.Ride;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface RideRepository {

    Optional<Ride> findById(UUID uuid);
    Page<Ride> findByUserId(UUID uuid, Pageable pageable);
    int acceptRide(UUID rideId, UUID driverId);
    Ride save(Ride e);
}
