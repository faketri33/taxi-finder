package org.faketri.infrastructure.client.location;


import org.faketri.domain.entity.DispatchState;
import reactor.core.publisher.Flux;

import java.util.UUID;


public interface LocationClient {
    Flux<UUID> getRiderNearby(DispatchState state);
}
