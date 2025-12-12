package org.faketri.domain.event;

import org.springframework.context.ApplicationEvent;

import java.util.UUID;

public class StopDispatchingEvent extends ApplicationEvent {
    private final UUID rideId;

    public StopDispatchingEvent(Object source, UUID rideId) {
        super(source);
        this.rideId = rideId;
    }

    public UUID getRideId() {
        return rideId;
    }
}
