package org.faketri.domain.event.ride;

import dto.ride.RideResponseDto;
import org.springframework.context.ApplicationEvent;

public class RideCreateEvent extends ApplicationEvent {
    private static final String EVENT_NAME = "ride.create";
    private final RideResponseDto rideResponse;

    public RideCreateEvent(Object source, RideResponseDto rideResponse) {
        super(source);
        this.rideResponse = rideResponse;
    }

    public String getEventName(){
        return EVENT_NAME;
    }

    public RideResponseDto getRideResponse() {
        return rideResponse;
    }
}
