package org.faketri.domain.event;

import dto.ride.RideResponseDto;
import org.faketri.domain.event.ride.RideCreateEvent;
import org.faketri.infrastructure.kafka.KafkaProducer;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class RideEventListener {

    private final KafkaProducer<RideResponseDto> kafkaProducer;

    public RideEventListener(KafkaProducer<RideResponseDto>  kafkaProducer) {
        this.kafkaProducer = kafkaProducer;
    }

    @EventListener(RideCreateEvent.class)
    public void rideCreateEvent(RideCreateEvent e){
        kafkaProducer.sendMessage(e.getEventName(), e.getRideResponse());
    }
}
