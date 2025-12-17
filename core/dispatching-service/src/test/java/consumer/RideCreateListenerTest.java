package consumer;

import dto.CarType;
import dto.address.AddressResponseDto;
import dto.ride.RideResponseDto;
import dto.rideStatus.RideStatus;
import org.faketri.ApplicationMain;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.test.context.EmbeddedKafka;

import java.util.UUID;

@SpringBootTest(classes = ApplicationMain.class, properties = {
        "spring.data.redis.port=6379",
        "spring.data.redis.host=localhost",
        "LOCATION_URL=http://localhost:3000"
})
@EmbeddedKafka(partitions = 1, topics = { "ride.create" })
class RideCreateListenerTest {
    @Autowired
    private KafkaTemplate<String, RideResponseDto> kafkaTemplate;

    @Test
    void onRideAccepted() {
        RideResponseDto ride = new RideResponseDto();
        ride.setCarType(CarType.ECONOMY);
        ride.setStatus(RideStatus.CREATE);
        ride.setId(UUID.randomUUID());
        ride.setStartAddress(new AddressResponseDto(55.347797, 42.013022, "","","","","","",""));
        kafkaTemplate.send("ride.create", ride);
    }
}