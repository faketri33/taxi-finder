package org.faketri.domain.repository;

import org.faketri.domain.entity.driver.Profile;

import java.util.Optional;
import java.util.UUID;

public interface ProfileRepository {

    Optional<Profile> get(UUID uid);

    Boolean verificationApprove(UUID uid);
    Boolean verificationFailure(UUID uid);

    Profile save(Profile p);
}
