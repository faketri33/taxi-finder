package org.faketri.api.controller;

import org.faketri.api.dto.ProfileResponseDto;
import org.faketri.domain.entity.mapper.ProfileMapper;
import org.faketri.usecase.ProfileService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/profile")
public class DriverProfileController {

    private final ProfileService profileService;
    private final ProfileMapper profileMapper;

    public DriverProfileController(ProfileService profileService, ProfileMapper profileMapper) {
        this.profileService = profileService;
        this.profileMapper = profileMapper;
    }

    @GetMapping("/me")
    public ResponseEntity<ProfileResponseDto> me(final Principal principal){
        if(principal.getName().isEmpty()) return ResponseEntity.notFound().build();

        final var profile = profileService.get(UUID.fromString(principal.getName())).map(profileMapper::toDto);

        return profile.map(ResponseEntity::ok).
                orElseGet(() -> ResponseEntity.notFound().build());
    }
}
