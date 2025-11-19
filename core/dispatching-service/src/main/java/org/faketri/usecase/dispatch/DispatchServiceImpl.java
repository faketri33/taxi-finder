package org.faketri.usecase.dispatch;

import org.faketri.domain.entity.dispatch.gateway.DispatchRepository;
import org.faketri.domain.entity.dispatch.model.DispatchState;
import org.faketri.infrastructure.ride.gateway.DispatchService;
import reactor.core.publisher.Mono;

import java.util.UUID;

public class DispatchServiceImpl implements DispatchService {

    private final DispatchRepository dispatchRepository;

    public DispatchServiceImpl(DispatchRepository dispatchRepository) {
        this.dispatchRepository = dispatchRepository;
    }

    @Override
    public Mono<DispatchState> findById(UUID id) {
        return null;
    }

    @Override
    public Mono<Void> save(DispatchState e) {
        return dispatchRepository.save(e).then(dispatch(e));
    }

    @Override
    public Mono<Void> dispatch(DispatchState e) {
        // TODO : Request to location service, and get drivers,
        //  send request to notif service for drivers;
        return null;
    }
}
