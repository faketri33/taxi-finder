package org.faketri.usecase.ride;

import jakarta.transaction.Transactional;
import org.faketri.api.dto.mapper.RideMapper;
import org.faketri.domain.exception.RideNotFoundException;
import org.faketri.domain.model.address.Address;
import org.faketri.domain.model.ride.Ride;
import org.faketri.domain.repository.RideRepository;
import org.faketri.usecase.price.DefaultPricePolicy;
import org.faketri.usecase.price.PriceCalculate;
import org.faketri.usecase.price.SubscriberPricePolicy;
import org.faketri.utils.GeoUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
public class RideServiceImpl implements RideService {
    private final RideRepository rideRepository;
    private final RideMapper rideMapper;

    public RideServiceImpl(RideRepository rideRepository, RideMapper rideMapper) {
        this.rideRepository = rideRepository;
        this.rideMapper = rideMapper;
    }

    @Override
    public Ride get(UUID rideId) {
        if (rideId == null) return null;
        return rideRepository
                .findById(rideId)
                .map(rideMapper::toDomain)
                .orElseThrow(() -> new RideNotFoundException("Ride with id: " + rideId + " not found"));
    }

    @Override
    public Ride save(Ride ride) {
        BigDecimal price = priceCalculate(ride.getStartAddress(), ride.getEndAddress(), new SubscriberPricePolicy());
        ride.setPrice(price);
        var saved = rideRepository.save(rideMapper.toEntity(ride));
        return rideMapper.toDomain(saved);
    }

    @Override
    public BigDecimal priceCalculate(Address start, Address end, DefaultPricePolicy policy) {
        double distance = GeoUtils.distanceKm(start.getLatitude(), start.getLongitude(), end.getLatitude(), end.getLongitude());
        return BigDecimal.valueOf(PriceCalculate.calculate(distance, policy));
    }

    @Override
    public Page<Ride> userHistoryRides(UUID userId, Pageable pageable) {
        if (pageable == null || userId == null) return null;
        return rideRepository.findByUserId(userId, pageable)
                .map(rideMapper::toDomain);
    }

    @Override
    @Transactional
    public boolean accept(UUID ride, UUID driverId) {
        var searchRide = get(ride);
        searchRide.accept();
        return rideRepository.acceptRide(ride, driverId) > 0;
    }

    public boolean cancel(UUID ride) {
        var searchRide = get(ride);
        searchRide.cancel();
        return rideRepository.cancel(ride) > 0;
    }
}
