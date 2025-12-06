package org.faketri.infrastructure.kafka.consumer;

import dto.ride.RideResponseDto;
import org.faketri.domain.entity.dispatch.model.DispatchState;
import org.faketri.domain.event.FindNearbyDriver;
import org.faketri.infrastructure.ride.gateway.DispatchService;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Set;

@Service
public class RideCreateListener {
    private final DispatchService dispatchService;
    private final ApplicationEventPublisher eventPublisher;

    public RideCreateListener(DispatchService dispatchService, ApplicationEventPublisher eventPublisher) {
        this.dispatchService = dispatchService;
        this.eventPublisher = eventPublisher;
    }

    @KafkaListener(topics = "ride.create")
    public Mono<Void> onRideAccepted(RideResponseDto ride) {

        DispatchState state = new DispatchState(
                ride.getId(),
                Set.of(),
                ride.getStartAddress(),
                ride.getEndAddress(),
                ride.getCarType(),
                ride.getStatus()
        );
        return dispatchService.save(state).doOnNext(current ->
                eventPublisher.publishEvent(new FindNearbyDriver(this, state))
        ).then();
    }
}
