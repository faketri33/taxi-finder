package org.faketri.infrastructure.kafka;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaProducerImpl<G> implements KafkaProducer<G> {
    private final KafkaTemplate<String, G> kafkaTemplate;

    public KafkaProducerImpl(KafkaTemplate<String, G> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void sendMessage(String topic, G message) {
        kafkaTemplate.send(topic, message);
    }
}
