package org.faketri.infrastructure.kafka.consumer;

import dto.rideStatus.RideStatus;
import org.faketri.infrastructure.ride.gateway.DispatchService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
public class RideAcceptListener {

    private final DispatchService dispatchService;

    public RideAcceptListener(DispatchService dispatchService) {
        this.dispatchService = dispatchService;
    }

    @KafkaListener(topics = "ride.accepted")
    public Mono<Void> onRideAccepted(UUID rideId) {
        return dispatchService.findById(rideId)
                .flatMap(state -> {
                    state.setStatus(RideStatus.ACCEPTED);
                    return dispatchService.save(state);
                })
                .then();
    }
}
