package org.faketri.infrastructure.persistence.repository;

import dto.rideStatus.RideStatus;
import org.faketri.domain.repository.RideRepository;
import org.faketri.infrastructure.persistence.entity.ride.model.RideEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class RideRepositoryImpl implements RideRepository {
    private final JpaRideRepository jpaRideRepository;

    public RideRepositoryImpl(JpaRideRepository rideRepository) {
        this.jpaRideRepository = rideRepository;
    }

    @Override
    public Optional<RideEntity> findById(UUID uuid) {
        return jpaRideRepository.findById(uuid);
    }

    @Override
    public Page<RideEntity> findByUserId(UUID uuid, Pageable pageable) {
        return jpaRideRepository.findByUserId(uuid, pageable);
    }

    @Override
    public int acceptRide(UUID rideId, UUID driverId) {
        return jpaRideRepository.rideAccept(driverId, RideStatus.ACCEPTED, rideId, RideStatus.DISPATCHING);
    }

    @Override
    public int cancel(UUID rideId) {
        return jpaRideRepository.cancel(rideId, RideStatus.CANCEL);
    }

    @Override
    public RideEntity save(RideEntity e) {
        return jpaRideRepository.save(e);
    }
}
