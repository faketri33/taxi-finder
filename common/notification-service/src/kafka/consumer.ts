import { kafka } from "./kafka.js";

const consumer = kafka.consumer({ groupId: "notification-group" });

export async function startConsumer() {
  await consumer.connect();
  await consumer.subscribe({ topic: "notify.send" });

  await consumer.run({
    eachMessage: async ({ message }) => {
      if (!message.value) return;

      const event = JSON.parse(message.value.toString());
      console.log("Notification received:", event);
    },
  });
}
