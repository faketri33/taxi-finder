package org.faketri.infrastructure.notification.gateway;

import reactor.core.publisher.Mono;

import java.util.UUID;

public interface NotificationClient {
    Mono<Void> notifyDriver(UUID driverId, UUID rideId);
}
