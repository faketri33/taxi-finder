package org.faketri;

import dto.CarType;
import dto.address.AddressResponseDto;
import dto.ride.RideResponseDto;
import dto.rideStatus.RideStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.UUID;

@SpringBootApplication
public class MainApplication {
    private static final Logger log = LoggerFactory.getLogger(MainApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class);
    }

    @Bean
    CommandLineRunner commandLineRunner(KafkaTemplate<String, RideResponseDto> kafkaTemplate) {
        return args -> {
            log.info("start");
            RideResponseDto ride = new RideResponseDto();
            ride.setCarType(CarType.ECONOMY);
            ride.setStatus(RideStatus.CREATE);
            ride.setId(UUID.randomUUID());
            ride.setStartAddress(new AddressResponseDto(55.347797, 42.013022, "","","","","","",""));
            log.info("Ride create id : {}", ride.getId());
            kafkaTemplate.send("ride.create", ride);
        };
    }
}
