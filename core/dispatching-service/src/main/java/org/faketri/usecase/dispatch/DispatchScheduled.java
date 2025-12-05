package org.faketri.usecase.dispatch;

import dto.rideStatus.RideStatus;
import org.faketri.domain.event.StartDispatchForRide;
import org.faketri.infrastructure.ride.gateway.DispatchService;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
public class DispatchScheduled {

    private final DispatchService dispatchService;

    public DispatchScheduled(DispatchService dispatchService) {
        this.dispatchService = dispatchService;
    }

    @EventListener(StartDispatchForRide.class)
    public Mono<Void> scheduleRoundTimer(StartDispatchForRide event) {
        UUID rideId = event.getRiderId();

        return Mono.delay(event.getDuration())
                .flatMap(t -> dispatchService.findById(rideId))
                .flatMap(current -> {

                    if (!RideStatus.DISPATCHING.equals(current.getStatus())) {
                        return Mono.empty();
                    }

                    if (current.getRound() >= 4) {
                        current.setStatus(RideStatus.FAILED);
                        return dispatchService.save(current).then();
                    }

                    current.setRound(current.getRound() + 1);
                    return dispatchService.save(current);
                })
                .then();
    }
}
