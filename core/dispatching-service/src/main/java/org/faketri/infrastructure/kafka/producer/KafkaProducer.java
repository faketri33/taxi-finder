package org.faketri.infrastructure.kafka.producer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class KafkaProducer{
    private static final Logger log = LoggerFactory.getLogger(KafkaProducer.class);
    private final KafkaTemplate<String, UUID> kafkaTemplate;

    public KafkaProducer(KafkaTemplate<String, UUID> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendFailure(UUID uuid){
        log.info("failed find driver for ride {}", uuid);
        kafkaTemplate.send("ride.failure", uuid);
    }

    public void sendNotification(List<UUID> driverForNotification, UUID rideId){
        driverForNotification.forEach(driver ->
            kafkaTemplate.send("notify.send", driver)
        );
    }
}
