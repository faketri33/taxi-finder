package org.faketri.domain.event;

import org.springframework.context.ApplicationEvent;

import java.time.Duration;
import java.util.List;
import java.util.UUID;

public class StartDispatchForRide extends ApplicationEvent {

    private final UUID riderId;
    private final List<UUID> drivers;
    private final Duration duration;

    public StartDispatchForRide(Object source, UUID riderId, List<UUID> drivers, Duration duration) {
        super(source);
        this.riderId = riderId;
        this.drivers = drivers;
        this.duration = duration;
    }

    public UUID getRiderId() {
        return riderId;
    }

    public List<UUID> getDrivers() {
        return drivers;
    }

    public Duration getDuration() {
        return duration;
    }
}
