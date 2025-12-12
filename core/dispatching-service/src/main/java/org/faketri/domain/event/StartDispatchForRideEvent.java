package org.faketri.domain.event;

import org.springframework.context.ApplicationEvent;

import java.util.List;
import java.util.UUID;

public class StartDispatchForRideEvent extends ApplicationEvent {

    private final transient UUID riderId;
    private final transient List<UUID> drivers;

    public StartDispatchForRideEvent(Object source, UUID riderId, List<UUID> drivers) {
        super(source);
        this.riderId = riderId;
        this.drivers = drivers;
    }

    public UUID getRiderId() {
        return riderId;
    }

    public List<UUID> getDrivers() {
        return drivers;
    }
}
