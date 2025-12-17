import {Kafka}  from "kafkajs";

export const env = {
  kafka: {
    brokers: process.env.KAFKA_SERVERS?.split(",") ?? ["localhost:29092"],
    clientId: process.env.KAFKA_CLIENT_ID ?? "notification-service"
  }
}

export const kafka = new Kafka({
  clientId: env.kafka.clientId,
  brokers: env.kafka.brokers,
});
