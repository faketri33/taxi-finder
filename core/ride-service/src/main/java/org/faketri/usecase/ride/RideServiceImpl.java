package org.faketri.usecase.ride;

import dto.rideStatus.RideStatus;
import jakarta.transaction.Transactional;
import org.faketri.api.dto.mapper.RideMapper;
import org.faketri.domain.exception.RideAcceptException;
import org.faketri.domain.exception.RideNotFoundException;
import org.faketri.domain.model.ride.Ride;
import org.faketri.domain.repository.RideRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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
        var saved = rideRepository.save(rideMapper.toEntity(ride));
        return rideMapper.toDomain(saved);
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
        if (!searchRide.getStatus().equals(RideStatus.DISPATCHING))
            throw new RideAcceptException("Ride with id: " + ride + " is not dispatching");
        return rideRepository.acceptRide(ride, driverId) > 0;
    }
}
