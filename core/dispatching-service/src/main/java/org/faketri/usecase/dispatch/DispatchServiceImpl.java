package org.faketri.usecase.dispatch;

import org.faketri.domain.entity.dispatch.gateway.DispatchRepository;
import org.faketri.domain.entity.dispatch.model.DispatchState;
import org.faketri.domain.event.FindNearbyDriver;
import org.faketri.infrastructure.ride.gateway.DispatchService;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
public class DispatchServiceImpl implements DispatchService {

    private final DispatchRepository dispatchRepository;

    public DispatchServiceImpl(DispatchRepository dispatchRepository)
    {
        this.dispatchRepository = dispatchRepository;
    }

    @Override
    public Mono<DispatchState> findById(UUID id) {
        return dispatchRepository.findById(id);
    }

    @Override
    public Mono<DispatchState> save(DispatchState e) {
        return dispatchRepository.save(e);
    }
}
