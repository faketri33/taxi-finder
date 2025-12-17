package org.faketri.infrastructure.client.notification.gateway;

import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

public interface NotificationClient {
    Mono<Void> notifyDriver(List<UUID> driverId, UUID rideId);
}
