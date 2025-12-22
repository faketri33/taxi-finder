package org.faketri.api.controller;

import dto.ride.RideResponseDto;
import jakarta.ws.rs.PathParam;
import org.faketri.api.dto.mapper.RideMapper;
import org.faketri.api.dto.request.RideRequestDto;
import org.faketri.usecase.ride.RideService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/ride")
public class RideController {
    private final RideService rideService;
    private final RideMapper rideMapper;

    public RideController(RideService rideService, RideMapper rideMapper) {
        this.rideService = rideService;
        this.rideMapper = rideMapper;
    }

    @PostMapping("/{userId}/request")
    public ResponseEntity<RideResponseDto> request(@PathVariable("userId") UUID userId, @RequestBody RideRequestDto rideRequestDto){
        var e = rideMapper.toDomain(rideRequestDto);
        e.setUserId(userId);
        return ResponseEntity.ok(rideMapper.toResponse(rideService.save(e)));
    }

    @PostMapping("/{driverId}/accept")
    public ResponseEntity<Boolean> accept(@PathVariable("driverId") UUID userId, @PathParam("rideId") UUID rideId){
        return ResponseEntity.ok(rideService.accept(rideId, userId));
    }

    @GetMapping("/{userId}/history")
    public ResponseEntity<Page<RideResponseDto>> getHistory(@PathVariable("userId") UUID userId){
        return ResponseEntity.ok(rideService
                .userHistoryRides(userId, Pageable.ofSize(20))
                .map(rideMapper::toResponse)
        );
    }
}
