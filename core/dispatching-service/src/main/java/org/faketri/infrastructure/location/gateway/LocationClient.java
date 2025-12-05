package org.faketri.infrastructure.location.gateway;


import org.faketri.domain.event.FindNearbyDriver;
import org.springframework.context.event.EventListener;
import reactor.core.publisher.Mono;

public interface LocationClient {
    @EventListener(FindNearbyDriver.class)
    Mono<Void> getRiderNearby(FindNearbyDriver f);
}
