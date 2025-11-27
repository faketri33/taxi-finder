package org.faketri.domain.messages;

public interface KafkaProducer {
    void sendMessage(String topic, Object message);
}
