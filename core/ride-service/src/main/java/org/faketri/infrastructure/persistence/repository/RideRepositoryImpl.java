package org.faketri.infrastructure.persistence.repository;

import dto.rideStatus.RideStatus;
import jakarta.transaction.Transactional;
import org.faketri.domain.model.ride.Ride;
import org.faketri.infrastructure.persistence.entity.ride.model.RideEntity;
import org.faketri.domain.event.ride.RideCreateEvent;
import org.faketri.api.dto.mapper.RideMapper;
import org.faketri.domain.repository.RideRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Repository
public class RideRepositoryImpl implements RideRepository {
    private final JpaRideRepository jpaRideRepository;
    private final RideMapper rideMapper;

    public RideRepositoryImpl(JpaRideRepository rideRepository, RideMapper rideMapper) {
        this.jpaRideRepository = rideRepository;
        this.rideMapper = rideMapper;
    }

    @Override
    public Optional<Ride> findById(UUID uuid) {
        return jpaRideRepository.findById(uuid)
                .map(rideMapper::toDomain);
    }

    @Override
    public Page<Ride> findByUserId(UUID uuid, Pageable pageable) {
        return jpaRideRepository.findByUserId(uuid, pageable).map(
                rideMapper::toDomain
        );
    }

    @Override
    @Transactional
    public int acceptRide(UUID rideId, UUID driverId) {
        return jpaRideRepository.rideAccept(driverId, RideStatus.ACCEPTED, rideId, RideStatus.DISPATCHING);
    }

    @Override
    public Ride save(Ride e) {
        var rideEntity = jpaRideRepository.save(rideMapper.toEntity(e));
        return rideMapper.toDomain(rideEntity);
    }
}
