package org.faketri.usecase.handler;

import dto.rideStatus.RideStatus;
import org.faketri.domain.exception.RoundCountLimitException;
import org.faketri.infrastructure.kafka.producer.KafkaFailedDispatch;
import org.faketri.usecase.dispatch.DispatchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Component
public class DispatchErrorHandler {

    private static final Logger log = LoggerFactory.getLogger(DispatchErrorHandler.class);
    private final DispatchService dispatchService;
    private final KafkaFailedDispatch kafkaFailedDispatch;

    public DispatchErrorHandler(DispatchService dispatchService, KafkaFailedDispatch kafkaFailedDispatch) {
        this.dispatchService = dispatchService;
        this.kafkaFailedDispatch = kafkaFailedDispatch;
    }

    @ExceptionHandler
    public void roundCountLimitExceptionHandler(RoundCountLimitException e) {
        var state = e.getDispatchState();
        state.setStatus(RideStatus.FAILED);
        log.info("stop dispatch {}", state.getRideId());
        dispatchService.save(state)
                .doOnNext(saved -> kafkaFailedDispatch.sendFailure(saved.getRideId()))
                .subscribe();
    }
}
