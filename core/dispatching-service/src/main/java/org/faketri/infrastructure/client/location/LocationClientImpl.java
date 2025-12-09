package org.faketri.infrastructure.client.location;

import org.faketri.domain.entity.DispatchState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriBuilder;
import reactor.core.publisher.Flux;

import java.net.URI;
import java.util.UUID;

@Component
public class LocationClientImpl  implements LocationClient {

    private static final int DEFAULT_DISPATCH_DISTANCE = 3000;
    private static final int DEFAULT_DISPATCH_DRIVER_LIMIT = 20;
    private static final Logger log = LoggerFactory.getLogger(LocationClientImpl.class);

    private final WebClient webClient;

    public LocationClientImpl(WebClient webClient) {
        this.webClient = webClient;
    }

    @Override
    public Flux<UUID> getRiderNearby(DispatchState state) {
        log.info("send request to location service");
        return webClient
                .get()
                .uri(uriBuilder -> uriBuilder(uriBuilder, state))
                .retrieve()
                .bodyToFlux(UUID.class);
    }

    private URI uriBuilder(UriBuilder uriBuilder, DispatchState r) {
        return uriBuilder
                .path("/drivers/nearby")
                .queryParam("distance", DEFAULT_DISPATCH_DISTANCE * r.getRound())
                .queryParam("carType", r.getCarType())
                .queryParam("status", "free")
                .queryParam("limit", DEFAULT_DISPATCH_DRIVER_LIMIT * r.getRound())
                .queryParam("lat", r.getAddressStart().getLatitude())
                .queryParam("lon", r.getAddressStart().getLongitude())
                .queryParam("exclude", r.getDriverNotificationSend())
                .build();
    }


}
