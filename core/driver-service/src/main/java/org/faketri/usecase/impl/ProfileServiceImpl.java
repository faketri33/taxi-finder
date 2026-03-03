package org.faketri.usecase.impl;

import org.faketri.domain.entity.driver.Profile;
import org.faketri.domain.repository.ProfileRepository;
import org.faketri.usecase.ProfileService;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class ProfileServiceImpl implements ProfileService {

    private final ProfileRepository profileRepository;

    public ProfileServiceImpl(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    @Override
    public Optional<Profile> get(UUID uid) {
        return profileRepository.get(uid);
    }

    @Override
    public Boolean verificationApprove(UUID uid) {
        return profileRepository.verificationApprove(uid);
    }

    @Override
    public Boolean verificationFailure(UUID uid) {
        return profileRepository.verificationFailure(uid);
    }

    @Override
    public Profile save(Profile p) {
        return profileRepository.save(p);
    }
}
