package org.faketri.usecase.dispatch;

import dto.rideStatus.RideStatus;
import org.faketri.domain.event.FindNearbyDriver;
import org.faketri.domain.event.StartDispatchForRide;
import org.faketri.infrastructure.ride.gateway.DispatchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.UUID;

@Component
public class DispatchScheduled {

    private static final Logger log = LoggerFactory.getLogger(DispatchScheduled.class);
    private final DispatchService dispatchService;
    private final ApplicationEventPublisher applicationEventPublisher;

    public DispatchScheduled(DispatchService dispatchService, ApplicationEventPublisher applicationEventPublisher) {
        this.dispatchService = dispatchService;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @EventListener
    private Mono<Void> runRoundTimer(StartDispatchForRide event) {
        UUID rideId = event.getRiderId();
        log.info("Starting round timer for ride {}", rideId);

        return Mono.delay(event.getDuration())
                .publishOn(Schedulers.boundedElastic())
                .flatMap(t -> dispatchService.findById(rideId))
                .flatMap(current -> {
                    log.info(current.toString());
                    if (!RideStatus.DISPATCHING.equals(current.getStatus())) {
                        log.info("Ride {} is no longer dispatching, stopping timer", rideId);
                        return Mono.empty();
                    }

                    if (current.getRound() >= 4) {
                        log.info("Max rounds reached for ride {}, marking as FAILED", rideId);
                        current.setStatus(RideStatus.FAILED);
                        return dispatchService.save(current).then();
                    }

                    log.info("Sending notifications for ride {}, round {}", rideId, current.getRound() + 1);
                    current.setRound(current.getRound() + 1);

                    return dispatchService.save(current)
                            .doOnNext(saved ->
                                    applicationEventPublisher.publishEvent(
                                            new FindNearbyDriver(this, saved)))
                            .then();
                });
    }

}
