package org.faketri.infrastructure.kafka.producer;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class KafkaFailedDispatch {
    private final KafkaTemplate<String, UUID> kafkaTemplate;


    public KafkaFailedDispatch(KafkaTemplate<String, UUID> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendFailure(UUID uuid){
        kafkaTemplate.send("ride.failure", uuid);
    }
}
