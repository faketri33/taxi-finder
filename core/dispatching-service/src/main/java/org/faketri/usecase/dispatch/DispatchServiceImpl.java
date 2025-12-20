package org.faketri.usecase.dispatch;

import dto.rideStatus.RideStatus;
import org.faketri.domain.entity.DispatchState;
import org.faketri.infrastructure.kafka.producer.KafkaProducer;
import org.faketri.infrastructure.persistence.repository.DispatchRepository;
import org.faketri.usecase.mapper.DispatchStateMapper;
import org.faketri.usecase.policy.DispatchStatePolicy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Service
public class  DispatchServiceImpl implements DispatchService {

    private static final Logger log = LoggerFactory.getLogger(DispatchServiceImpl.class);
    private final DispatchRepository dispatchRepository;
    private final DispatchStateMapper dispatchStateMapper;
    private final DispatchScheduled dispatchScheduled;
    private final KafkaProducer kafkaFailedDispatch;

    public DispatchServiceImpl(DispatchRepository dispatchRepository, DispatchStateMapper dispatchStateMapper, DispatchScheduled dispatchScheduled, KafkaProducer kafkaFailedDispatch) {
        this.dispatchRepository = dispatchRepository;
        this.dispatchStateMapper = dispatchStateMapper;
        this.dispatchScheduled = dispatchScheduled;
        this.kafkaFailedDispatch = kafkaFailedDispatch;
    }

    @Override
    public Mono<DispatchState> get(UUID id) {
        if (id == null) return Mono.empty();
        log.info("find dispatch {}", id);
        return dispatchRepository
                .findById(id)
                .map(dispatchStateMapper::toDomain);
    }

    @Override
    public Mono<Void> dispatch(UUID dispatchId) {
        if (dispatchId == null) {
            log.warn("dispatchId is null");
            return Mono.empty();
        }

        return Mono.defer(() ->
                get(dispatchId)
                    .flatMap(state -> {
                        boolean maxRoundNotExceededLimit = DispatchStatePolicy.getMaxRound() < state.getRound();
                        boolean statusIsNotDispatching = !state.getStatus().equals(RideStatus.DISPATCHING);
                        if (maxRoundNotExceededLimit || statusIsNotDispatching) {
                            log.info("dispatch round not limited {}, status is dispatch {}, round {}, rideId {}", maxRoundNotExceededLimit, statusIsNotDispatching, state.getRound(), dispatchId);
                           return stopDispatch(dispatchId);
                        }
                        return dispatchScheduled.findDriverSendNotification(state).flatMap(this::nextRound);
                    })
                    .onErrorResume(e -> {
                        log.error("Error during dispatch {}: {}", dispatchId, e.getMessage(), e);
                        return stopDispatch(dispatchId)
                                .onErrorResume(err -> {
                                    log.error("Error while stopping dispatch {} after failure: {}", dispatchId, err.getMessage(), err);
                                    return Mono.empty();
                                });
                    })
        ).then();
    }

    @Override
    public Mono<Void> stopDispatch(UUID dispatchId) {
        if (dispatchId == null) {
            log.warn("stopDispatch called with null id");
            return Mono.empty();
        }

        return get(dispatchId)
                .flatMap(state -> {
                    if (state.getStatus() != null && state.getStatus().equals(RideStatus.ACCEPTED)) {
                        log.info("Ride {} already accepted; skipping failure publish.", dispatchId);
                        return Mono.empty();
                    }
                    return Mono.fromRunnable(() -> kafkaFailedDispatch.sendFailure(dispatchId));
                }).then(dispatchRepository.deleteById(dispatchId))
                .doOnSuccess(v -> log.info("Stopped dispatching for ride {}", dispatchId))
                .doOnError(e -> log.error("Failed to stop dispatch {}: {}", dispatchId, e.getMessage(), e))
                .then();
    }

    private Mono<Void> nextRound(DispatchState dispatchState){
        String timeExpires = dispatchState.getRoundExpiresAt().format(DateTimeFormatter.ISO_LOCAL_TIME);
        log.info("time to start next round {}", timeExpires);
        var delay = Duration.between(LocalDateTime.now(), dispatchState.getRoundExpiresAt());
        if (delay.isNegative() || delay.isZero()) delay = Duration.ofSeconds(30);
        log.info("delay time {}", delay.toSecondsPart());
        return save(dispatchState)
                .delayElement(delay.abs())
                .then(dispatch(dispatchState.getRideId()));
    }

    @Override
    public Mono<DispatchState> save(DispatchState e) {
        log.info("Save dispatch state : {}", e.getRideId());
        return dispatchRepository
                .save(dispatchStateMapper.toEntity(e))
                .map(dispatchStateMapper::toDomain);
    }
}
