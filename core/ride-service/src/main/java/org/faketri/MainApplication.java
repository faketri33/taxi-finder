package org.faketri;

import dto.CarType;
import dto.address.AddressResponseDto;
import dto.ride.RideResponseDto;
import dto.rideStatus.RideStatus;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.UUID;

@SpringBootApplication
public class MainApplication {
    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class);
    }

    @Bean
    CommandLineRunner commandLineRunner(KafkaTemplate<String, RideResponseDto> kafkaTemplate) {
        return args -> {
            RideResponseDto ride = new RideResponseDto();
            ride.setCarType(CarType.ECONOMY);
            ride.setStatus(RideStatus.CREATE);
            ride.setId(UUID.randomUUID());
            ride.setStartAddress(new AddressResponseDto(55.347797, 42.013022, "","","","","","",""));

            kafkaTemplate.send("ride.create", ride);
        };
    }
}
