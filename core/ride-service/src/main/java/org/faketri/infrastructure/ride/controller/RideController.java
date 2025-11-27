package org.faketri.infrastructure.ride.controller;

import dto.ride.RideResponseDto;
import org.faketri.domain.dto.request.RideRequestDto;
import org.faketri.domain.mapper.RideMapper;
import org.faketri.infrastructure.ride.gateway.RideService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("api/v1/ride")
public class RideController {
    private final RideService rideService;

    public RideController(RideService rideService) {
        this.rideService = rideService;
    }

    @PostMapping("/{userId}/request")
    public ResponseEntity<RideResponseDto> request(@PathVariable("userId") UUID userId, @RequestBody RideRequestDto rideRequestDto){
        var e = RideMapper.toEntity(rideRequestDto);
        e.setUserId(userId);
        return ResponseEntity.ok(RideMapper.toDto(rideService.save(e)));
    }
}
