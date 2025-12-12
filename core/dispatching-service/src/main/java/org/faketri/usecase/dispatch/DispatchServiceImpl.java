package org.faketri.usecase.dispatch;

import dto.rideStatus.RideStatus;
import org.faketri.domain.entity.DispatchState;
import org.faketri.domain.event.StartDispatchForRideEvent;
import org.faketri.infrastructure.client.location.LocationClient;
import org.faketri.infrastructure.persistence.repository.DispatchRepository;
import org.faketri.usecase.mapper.DispatchStateMapper;
import org.faketri.usecase.policy.DispatchStatePolicy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

@Service
public class  DispatchServiceImpl implements DispatchService {

    private static final Logger log = LoggerFactory.getLogger(DispatchServiceImpl.class);
    private final DispatchRepository dispatchRepository;
    private final LocationClient locationClient;
    private final DispatchStateMapper dispatchStateMapper;
    private final ApplicationEventPublisher applicationEventPublisher;

    public DispatchServiceImpl(DispatchRepository dispatchRepository, DispatchStatePolicy dispatchStatePolicy, LocationClient locationClient, DispatchStateMapper dispatchStateMapper, DispatchScheduled dispatchScheduled, ApplicationEventPublisher applicationEventPublisher)
    {
        this.dispatchRepository = dispatchRepository;
        this.locationClient = locationClient;
        this.dispatchStateMapper = dispatchStateMapper;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Override
    public Mono<DispatchState> get(UUID id) {
        if (id == null) return Mono.empty();
        return dispatchRepository
                .findById(id)
                .map(dispatchStateMapper::toDomain);
    }

    @Override
    public Mono<Void> dispatch(UUID dispatchState) {
        return get(dispatchState)
                .expand(state -> {
                    if (!state.getStatus().equals(RideStatus.DISPATCHING))
                        return Mono.empty();
                    Duration delay = Duration.between(Instant.now(), state.getRoundExpiresAt());
                    return Mono.delay(delay.abs()).then(get(dispatchState));
                })
                .concatMap(state -> locationClient
                        .getRiderNearby(state)
                        .collectList()
                        .doOnNext(drivers -> applicationEventPublisher.publishEvent(
                                new StartDispatchForRideEvent(
                                        this,
                                        dispatchState,
                                        drivers)))
                        .thenReturn(state)
                ).then();
    }

    @Override
    public Mono<Boolean> stopDispatch(DispatchState dispatchState) {
        return dispatchRepository
                .deleteById(dispatchState.getRideId());
    }

    @Override
    public Mono<DispatchState> save(DispatchState e) {
        log.info("Save dispatch state : {}", e.getRideId());
        return dispatchRepository
                .save(dispatchStateMapper.toEntity(e))
                .map(dispatchStateMapper::toDomain);
    }
}
