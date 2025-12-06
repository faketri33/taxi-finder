package org.faketri.infrastructure.kafka;

public interface KafkaProducer<G> {
    void sendMessage(String topic, G message);
}
