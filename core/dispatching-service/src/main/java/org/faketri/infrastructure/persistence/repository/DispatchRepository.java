package org.faketri.infrastructure.persistence.repository;

import org.faketri.infrastructure.persistence.entity.DispatchStateEntity;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface DispatchRepository {

    Mono<DispatchStateEntity> findById(UUID id);

    Mono<DispatchStateEntity> save(DispatchStateEntity dispatchStateEntity);
}
