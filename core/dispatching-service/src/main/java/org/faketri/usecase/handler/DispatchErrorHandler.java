package org.faketri.usecase.handler;

import dto.rideStatus.RideStatus;
import org.faketri.domain.exception.RoundCountLimitException;
import org.faketri.usecase.dispatch.DispatchService;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Component
public class DispatchErrorHandler {

    private final DispatchService dispatchService;

    public DispatchErrorHandler(DispatchService dispatchService) {
        this.dispatchService = dispatchService;
    }

    @ExceptionHandler
    public void roundCountLimitExceptionHandler(RoundCountLimitException e) {
        var state = e.getDispatchState();
        state.setStatus(RideStatus.FAILED);
        dispatchService.save(state).subscribe();
    }
}
