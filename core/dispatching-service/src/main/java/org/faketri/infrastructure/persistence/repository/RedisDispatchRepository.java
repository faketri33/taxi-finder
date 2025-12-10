package org.faketri.infrastructure.persistence.repository;

import org.faketri.infrastructure.persistence.entity.DispatchStateEntity;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.core.ReactiveValueOperations;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
public class RedisDispatchRepository implements DispatchRepository {
    private final ReactiveRedisTemplate<String, DispatchStateEntity> reactiveRedisTemplate;
    private final ReactiveValueOperations<String, DispatchStateEntity> valueOps;

    public RedisDispatchRepository(ReactiveRedisTemplate<String, DispatchStateEntity> reactiveRedisTemplate) {
        this.reactiveRedisTemplate = reactiveRedisTemplate;
        this.valueOps = this.reactiveRedisTemplate.opsForValue();
    }

    private String key(UUID id) {
        return "dispatch:" + id;
    }

    @Override
    public Mono<DispatchStateEntity> findById(UUID id) {
        return valueOps.get(key(id));
    }

    @Override
    public Mono<DispatchStateEntity> save(DispatchStateEntity dispatchStateEntity) {
        return valueOps.set(key(dispatchStateEntity.getRideId()), dispatchStateEntity)
                .map(success -> dispatchStateEntity);
    }

    @Override
    public Mono<Boolean> deleteById(UUID id) {
        return valueOps.delete(key(id));
    }
}
