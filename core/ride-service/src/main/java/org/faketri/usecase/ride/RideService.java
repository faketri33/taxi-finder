package org.faketri.usecase.ride;

import org.faketri.domain.model.ride.Ride;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface RideService {
    Ride get(UUID rideId);

    Ride save(Ride ride);

    Page<Ride> userHistoryRides(UUID userId, Pageable pageable);

    boolean accept(UUID ride, UUID driverId);

}
