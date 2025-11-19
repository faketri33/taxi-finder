package org.faketri.infrastructure.messages;

public interface KafkaProducer {
    void sendMessage(String topic, Object message);
}
