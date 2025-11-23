package org.faketri.infrastructure.location.gateway;

import dto.CarType;
import reactor.core.publisher.Flux;

import java.util.UUID;

public interface LocationClient {
    Flux<UUID> getRiderNearby(double lat, double lon, CarType carType);
}
