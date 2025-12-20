package org.faketri.usecase.dispatch;

import org.faketri.domain.entity.DispatchState;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface DispatchService {
    Mono<DispatchState> get(UUID id);
    Mono<Void> dispatch(UUID dispatchState);
    Mono<Void> stopDispatch(UUID dispatchState);
    Mono<DispatchState> save(DispatchState e);
}
