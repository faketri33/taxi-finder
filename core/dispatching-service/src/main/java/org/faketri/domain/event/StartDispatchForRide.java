package org.faketri.domain.event;

import org.springframework.context.ApplicationEvent;

import java.time.Duration;
import java.util.Set;
import java.util.UUID;

public class StartDispatchForRide extends ApplicationEvent {

    private final UUID riderId;
    private final Set<UUID> drivers;
    private final Duration duration;

    public StartDispatchForRide(Object source, UUID riderId, Set<UUID> drivers, Duration duration) {
        super(source);
        this.riderId = riderId;
        this.drivers = drivers;
        this.duration = duration;
    }

    public UUID getRiderId() {
        return riderId;
    }

    public Set<UUID> getDrivers() {
        return drivers;
    }

    public Duration getDuration() {
        return duration;
    }
}
