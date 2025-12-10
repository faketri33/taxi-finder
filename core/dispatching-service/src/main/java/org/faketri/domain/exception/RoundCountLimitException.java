package org.faketri.domain.exception;

import org.faketri.domain.entity.DispatchState;

public class RoundCountLimitException extends RuntimeException {

    private final transient DispatchState dispatchState;

    public RoundCountLimitException(DispatchState dispatchState) {
        this.dispatchState = dispatchState;
    }

    public RoundCountLimitException(String message, DispatchState dispatchState) {
        super(message);
        this.dispatchState = dispatchState;
    }

    public DispatchState getDispatchState() {
        return dispatchState;
    }
}
