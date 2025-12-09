package org.faketri.domain.event;

import org.faketri.domain.entity.DispatchState;
import org.springframework.context.ApplicationEvent;


public class FindNearbyDriver extends ApplicationEvent {

    private final DispatchState dispatchState;

    public FindNearbyDriver(Object source, DispatchState dispatchState) {
        super(source);
        this.dispatchState = dispatchState;
    }

    public DispatchState getDispatchState() {
        return dispatchState;
    }
}
