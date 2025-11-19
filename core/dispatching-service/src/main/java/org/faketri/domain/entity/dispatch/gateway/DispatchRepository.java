package org.faketri.domain.entity.dispatch.gateway;

import org.faketri.domain.entity.dispatch.model.DispatchState;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import java.util.UUID;

public interface DispatchRepository extends ReactiveCrudRepository<DispatchState, UUID> {
}
