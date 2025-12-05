package org.faketri.domain.event;

import org.springframework.context.ApplicationEvent;

import java.time.Duration;
import java.util.UUID;

public class StartDispatchForRide extends ApplicationEvent {

    private final UUID riderId;
    private final Duration duration;

    public StartDispatchForRide(Object source, UUID riderId, Duration duration) {
        super(source);
        this.riderId = riderId;
        this.duration = duration;
    }

    public UUID getRiderId() {
        return riderId;
    }

    public Duration getDuration() {
        return duration;
    }
}
