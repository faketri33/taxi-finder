package org.faketri.usecase.handler;

import dto.rideStatus.RideStatus;
import org.faketri.domain.exception.RoundCountLimitException;
import org.faketri.usecase.dispatch.DispatchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Component
public class DispatchErrorHandler {

    private static final Logger log = LoggerFactory.getLogger(DispatchErrorHandler.class);
    private final DispatchService dispatchService;
    public DispatchErrorHandler(DispatchService dispatchService) {
        this.dispatchService = dispatchService;
    }

    @ExceptionHandler
    public void roundCountLimitExceptionHandler(RoundCountLimitException e) {
        var state = e.getDispatchState();
        state.setStatus(RideStatus.FAILED);
        log.info("stop dispatch {}", state.getRideId());
        dispatchService.stopDispatch(state).subscribe();
    }
}
