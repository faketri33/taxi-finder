package org.faketri.infrastructure.kafka.consumer;

import dto.ride.RideResponseDto;
import org.faketri.domain.entity.dispatch.model.DispatchState;
import org.faketri.infrastructure.ride.gateway.DispatchService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class RideCreateListener {
    private final DispatchService dispatchService;

    public RideCreateListener(DispatchService dispatchService) {
        this.dispatchService = dispatchService;
    }

    @KafkaListener(topics = "ride.create")
    public Mono<Void> onRideAccepted(RideResponseDto ride) {
        DispatchState state = new DispatchState(
                ride.getId(),
                List.of(),
                ride.getStartAddress(),
                ride.getEndAddress(),
                ride.getStatus()
        );
        return dispatchService.save(state);
    }
}
