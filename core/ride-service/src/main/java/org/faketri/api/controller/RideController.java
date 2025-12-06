package org.faketri.api.controller;

import dto.ride.RideResponseDto;
import org.faketri.api.dto.request.RideRequestDto;
import org.faketri.api.dto.mapper.RideMapper;
import org.faketri.domain.repository.RideRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("api/v1/ride")
public class RideController {
    private final RideRepository rideRepository;
    private final RideMapper rideMapper;

    public RideController(RideRepository rideRepository, RideMapper rideMapper) {
        this.rideRepository = rideRepository;
        this.rideMapper = rideMapper;
    }

    @PostMapping("/{userId}/request")
    public ResponseEntity<RideResponseDto> request(@PathVariable("userId") UUID userId, @RequestBody RideRequestDto rideRequestDto){
        var e = rideMapper.toDomain(rideRequestDto);
        e.setUserId(userId);
        return ResponseEntity.ok(rideMapper.toResponse(rideRepository.save(e)));
    }
}
