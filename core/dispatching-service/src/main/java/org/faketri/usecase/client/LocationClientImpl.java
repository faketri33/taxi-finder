package org.faketri.usecase.client;

import org.faketri.domain.event.FindNearbyDriver;
import org.faketri.domain.event.StartDispatchForRide;
import org.faketri.infrastructure.location.gateway.LocationClient;
import org.faketri.infrastructure.ride.gateway.DispatchService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.UUID;

@Component
public class LocationClientImpl  implements LocationClient {

    private final WebClient webClient;
    @Value("LOCATION_CLIENT_URL")
    private String baseUrl;
    private final DispatchService dispatchService;
    private final ApplicationEventPublisher applicationEventPublisher;

    public LocationClientImpl(WebClient webClient, DispatchService dispatchService, ApplicationEventPublisher applicationEventPublisher) {
        this.dispatchService = dispatchService;
        this.webClient = WebClient.builder().baseUrl(baseUrl).build();
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Override
    @EventListener(FindNearbyDriver.class)
    public Mono<Void> getRiderNearby(FindNearbyDriver f) {
        var r = f.getDispatchState();
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/drivers/nearby")
                        .queryParam("distance", 5000)
                        .queryParam("carType", r.getCarType())
                        .queryParam("status", r.getStatus())
                        .queryParam("limit", 20)
                        .queryParam("lat", r.getAddressStart().getLatitude())
                        .queryParam("lon", r.getAddressStart().getLongitude())
                        .build()
                )
                .retrieve()
                .bodyToFlux(UUID.class)
                .flatMap(drivers -> {
                    r.getDriverNotificationSend().add(drivers);
                    return dispatchService.save(r);
                })
                .then(Mono.fromRunnable(() -> {
                    applicationEventPublisher.publishEvent(
                            new StartDispatchForRide(this, r.getId(), Duration.ofSeconds(15))
                    );
                }));
    }

}
