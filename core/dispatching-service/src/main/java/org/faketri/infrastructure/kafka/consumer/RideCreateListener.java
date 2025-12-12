package org.faketri.infrastructure.kafka.consumer;

import dto.ride.RideResponseDto;
import dto.rideStatus.RideStatus;
import org.faketri.domain.entity.DispatchState;
import org.faketri.usecase.dispatch.DispatchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.time.Instant;
import java.util.Set;

@Service
public class RideCreateListener {
    private static final Logger log = LoggerFactory.getLogger(RideCreateListener.class);
    private final DispatchService dispatchService;

    public RideCreateListener(DispatchService dispatchService) {
        this.dispatchService = dispatchService;
    }

    @KafkaListener(topics = "ride.create")
    public Mono<Void> onRideAccepted(RideResponseDto ride) {
        log.info("Ride accepted id : {}", ride.getId());
        DispatchState state = new DispatchState(
                ride.getId(),
                Set.of(),
                ride.getStartAddress(),
                ride.getEndAddress(),
                ride.getCarType(),
                RideStatus.DISPATCHING
        );
        state.setRoundExpiresAt(Instant.now().plusSeconds(190));
        return dispatchService.save(state)
                .publishOn(Schedulers.boundedElastic())
                .flatMap(saved -> dispatchService.dispatch(saved.getRideId()))
                .then();
    }
}
