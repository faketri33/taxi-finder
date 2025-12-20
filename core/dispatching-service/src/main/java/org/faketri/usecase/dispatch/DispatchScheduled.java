package org.faketri.usecase.dispatch;

import org.faketri.domain.entity.DispatchState;
import org.faketri.infrastructure.client.location.LocationClient;
import org.faketri.infrastructure.client.notification.gateway.NotificationClient;
import org.faketri.usecase.policy.DispatchStatePolicy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class DispatchScheduled {

    private static final Logger log = LoggerFactory.getLogger(DispatchScheduled.class);
    private final NotificationClient notificationClient;
    private final LocationClient locationClient;
    private final DispatchStatePolicy dispatchStatePolicy;

    public DispatchScheduled(NotificationClient notificationClient, LocationClient locationClient, DispatchStatePolicy dispatchStatePolicy) {
        this.notificationClient = notificationClient;
        this.locationClient = locationClient;
        this.dispatchStatePolicy = dispatchStatePolicy;
    }

    public Mono<DispatchState> findDriverSendNotification(DispatchState state) {
        if (state == null) return Mono.empty();

        return locationClient.getRiderNearby(state)
                .collectList()
                .flatMap(drivers -> {
                    log.info("find drivers - {}", drivers);
                    state.incrementRound(dispatchStatePolicy);

                    if (drivers.isEmpty()) return Mono.just(state);

                    state.getDriverNotificationSend().addAll(drivers);
                    state.updateRoundTimeout();

                    return notificationClient
                            .notifyDriver(drivers, state.getRideId())
                            .then(Mono.just(state));
                })
                .doOnNext(s -> log.info("Round {} scheduled for ride {} finish", s.getRound() - 1, s.getRideId()))
                .doOnError(e -> log.error("runRoundTimer failed for ride {}: {}", state.getRideId(), e.getMessage(), e));
    }
}
