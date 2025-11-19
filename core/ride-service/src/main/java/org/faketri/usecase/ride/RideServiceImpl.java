package org.faketri.usecase.ride;

import org.faketri.domain.event.ride.RideCreateEvent;
import org.faketri.domain.entity.ride.gateway.RideRepository;
import org.faketri.domain.mapper.RideMapper;
import org.faketri.domain.entity.ride.model.Ride;
import org.faketri.infrastructure.ride.gateway.RideService;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class RideServiceImpl implements RideService {
    private final ApplicationEventPublisher eventPublisher;
    private final RideRepository rideRepository;

    public RideServiceImpl(ApplicationEventPublisher eventPublisher, RideRepository rideRepository) {
        this.eventPublisher = eventPublisher;
        this.rideRepository = rideRepository;
    }

    @Override
    public Optional<Ride> findById(UUID uuid) {
        return rideRepository.findById(uuid);
    }

    @Override
    public Page<Ride> findByUserId(UUID uuid, Pageable pageable) {
        return rideRepository.findByUserId(uuid, pageable);
    }

    @Override
    public Ride save(Ride e) {
        Ride s = rideRepository.save(e);
        if (s.getId() != null) eventPublisher.publishEvent(new RideCreateEvent(this, RideMapper.toDto(s)));
        return s;
    }
}
