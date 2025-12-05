package org.faketri.infrastructure.ride.gateway;

import org.faketri.domain.entity.dispatch.model.DispatchState;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface DispatchService {
    Mono<DispatchState> findById(UUID id);
    Mono<DispatchState> save(DispatchState e);
}
