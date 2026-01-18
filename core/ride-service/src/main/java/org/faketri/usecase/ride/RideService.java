package org.faketri.usecase.ride;

import org.faketri.domain.model.address.Address;
import org.faketri.domain.model.ride.Ride;
import org.faketri.usecase.price.DefaultPricePolicy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.UUID;

public interface RideService {
    Ride get(UUID rideId);
    Ride save(Ride ride);

    BigDecimal priceCalculate(Address start, Address end, DefaultPricePolicy policy);

    Page<Ride> userHistoryRides(UUID userId, Pageable pageable);

    boolean accept(UUID ride, UUID driverId);

}
