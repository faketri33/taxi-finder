package org.faketri.usecase.dispatch;

import dto.rideStatus.RideStatus;
import org.faketri.domain.entity.dispatch.model.DispatchState;
import org.faketri.infrastructure.ride.gateway.DispatchService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.UUID;

@Service
public class DispatchScheduled {

    private final DispatchService dispatchService;

    public DispatchScheduled(DispatchService dispatchService) {
        this.dispatchService = dispatchService;
    }

    public Mono<Void> scheduleRoundTimer(DispatchState state, Duration timeout) {
        UUID rideId = state.getId();

        return Mono.delay(timeout)
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
                    return dispatchService.save(current)
                            .then(dispatchService.dispatch(current));
                })
                .then();
    }
}
