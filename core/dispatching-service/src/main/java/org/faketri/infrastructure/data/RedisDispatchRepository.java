package org.faketri.infrastructure.data;

import org.faketri.domain.entity.dispatch.gateway.DispatchRepository;
import org.faketri.domain.entity.dispatch.model.DispatchState;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.core.ReactiveValueOperations;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
public class RedisDispatchRepository implements DispatchRepository {
    private final ReactiveRedisTemplate<String, DispatchState> reactiveRedisTemplate;
    private final ReactiveValueOperations<String, DispatchState> valueOps;

    public RedisDispatchRepository(ReactiveRedisTemplate<String, DispatchState> reactiveRedisTemplate) {
        this.reactiveRedisTemplate = reactiveRedisTemplate;
        this.valueOps = this.reactiveRedisTemplate.opsForValue();
    }

    private String key(UUID id) {
        return "dispatch:" + id;
    }

    @Override
    public Mono<DispatchState> findById(UUID id) {
        return valueOps.get(key(id));
    }

    @Override
    public Mono<DispatchState> save(DispatchState dispatchState) {
        return valueOps.set(key(dispatchState.getId()), dispatchState)
                .map(success -> dispatchState);
    }
}
