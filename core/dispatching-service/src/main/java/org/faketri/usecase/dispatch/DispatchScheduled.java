package org.faketri.usecase.dispatch;

import dto.rideStatus.RideStatus;
import org.faketri.domain.event.StartDispatchForRide;
import org.faketri.usecase.policy.DispatchStatePolicy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.UUID;

@Component
public class DispatchScheduled {

    private static final Logger log = LoggerFactory.getLogger(DispatchScheduled.class);
    private final DispatchStatePolicy dispatchStatePolicy;
    private final DispatchService dispatchService;

    public DispatchScheduled(DispatchStatePolicy dispatchStatePolicy, DispatchService dispatchService) {
        this.dispatchStatePolicy = dispatchStatePolicy;
        this.dispatchService = dispatchService;
    }

    @EventListener
    private Mono<Void> runRoundTimer(StartDispatchForRide event) {
        UUID rideId = event.getRiderId();
        log.info("Starting round timer for ride {}", rideId);
        if (event.getDrivers().isEmpty()) {
            log.info("No drivers found for ride {}", rideId);
            return dispatchService.get(event.getRiderId())
                    .flatMap(state -> {
                        state.incrementRound(dispatchStatePolicy);
                        return dispatchService.save(state).doOnNext(dispatchService::dispatch);
                    }).then();
        }

        return Mono.delay(event.getDuration())
                .publishOn(Schedulers.boundedElastic())
                .flatMap(t -> dispatchService.get(rideId))
                .flatMap(current -> {
                    log.info(current.toString());
                    if (!RideStatus.DISPATCHING.equals(current.getStatus())) {
                        log.info("Ride {} is no longer dispatching, stopping timer", rideId);
                        return Mono.empty();
                    }
                    log.info("Sending notifications for ride {}, round {}", rideId, current.getRound() + 1);
                    current.incrementRound(dispatchStatePolicy);
                    current.getDriverNotificationSend().addAll(event.getDrivers());
                    return dispatchService.save(current).doOnNext(dispatchService::dispatch);
                }).then();
    }
}
