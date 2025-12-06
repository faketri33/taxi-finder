package org.faketri.domain.entity.dispatch.gateway;

import org.faketri.domain.entity.dispatch.model.DispatchState;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface DispatchRepository {

    Mono<DispatchState> findById(UUID id);
    Mono<DispatchState> save(DispatchState dispatchState);
}
