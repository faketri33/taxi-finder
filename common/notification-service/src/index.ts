import { startConsumer } from "./kafka/consumer.js";

async function bootstrap() {
  await startConsumer();
  console.log("Notification service started");
}

await bootstrap();