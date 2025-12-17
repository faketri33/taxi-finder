package org.faketri.usecase.dispatch;

import dto.rideStatus.RideStatus;
import org.faketri.domain.event.StartDispatchForRideEvent;
import org.faketri.infrastructure.client.notification.gateway.NotificationClient;
import org.faketri.usecase.policy.DispatchStatePolicy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class DispatchScheduled {

    private static final Logger log = LoggerFactory.getLogger(DispatchScheduled.class);
    private final DispatchService dispatchService;
    private final NotificationClient notificationClient;
    private final DispatchStatePolicy dispatchStatePolicy;

    public DispatchScheduled(DispatchService dispatchService, NotificationClient notificationClient, DispatchStatePolicy dispatchStatePolicy) {
        this.dispatchService = dispatchService;
        this.notificationClient = notificationClient;
        this.dispatchStatePolicy = dispatchStatePolicy;
    }
    @EventListener
    public Mono<Void> runRoundTimer(StartDispatchForRideEvent event) {
        return dispatchService.get(event.getRiderId())
                .filter(s -> RideStatus.DISPATCHING.equals(s.getStatus()))
                .switchIfEmpty(Mono.fromRunnable(() ->
                        log.info("Ride {} is no longer dispatching", event.getRiderId())))
                .flatMap(s -> {
                    if (event.getDrivers().isEmpty()) {
                        log.info("No drivers found for ride {}", s.getRideId());
                    }
                    log.info("Sending notifications for ride {}, round {}", s.getRideId(), s.getRound() + 1);
                    s.getDriverNotificationSend().addAll(event.getDrivers());
                    s.incrementRound(dispatchStatePolicy);
                    s.updateRoundTimeout();
                    return dispatchService.save(s)
                            .then(notificationClient
                                    .notifyDriver(event.getDrivers(), event.getRiderId())
                            );
                });
    }
}
