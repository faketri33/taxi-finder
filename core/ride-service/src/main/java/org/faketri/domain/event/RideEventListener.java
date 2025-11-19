package org.faketri.domain.event;

import org.faketri.domain.event.ride.RideCreateEvent;
import org.faketri.infrastructure.messages.KafkaProducer;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class RideEventListener {

    private final KafkaProducer kafkaProducer;

    public RideEventListener(KafkaProducer kafkaProducer) {
        this.kafkaProducer = kafkaProducer;
    }

    @EventListener(RideCreateEvent.class)
    public void rideCreateEvent(RideCreateEvent e){
        kafkaProducer.sendMessage(e.getEventName(), e.getRideResponse());
    }
}
