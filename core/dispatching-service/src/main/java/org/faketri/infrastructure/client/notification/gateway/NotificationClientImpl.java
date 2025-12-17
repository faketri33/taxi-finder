package org.faketri.infrastructure.client.notification.gateway;

import org.faketri.infrastructure.kafka.producer.KafkaProducer;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

@Component
public class NotificationClientImpl implements NotificationClient
{
    private final KafkaProducer kafkaProducer;

    public NotificationClientImpl(KafkaProducer kafkaProducer) {
        this.kafkaProducer = kafkaProducer;
    }

    @Override
    public Mono<Void> notifyDriver(List<UUID> driverId, UUID rideId) {
        return Mono.fromRunnable(() -> kafkaProducer.sendNotification(driverId, rideId)).then();
    }
}
