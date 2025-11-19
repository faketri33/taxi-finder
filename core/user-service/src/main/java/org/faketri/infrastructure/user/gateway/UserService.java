package org.faketri.infrastructure.user.gateway;

import org.faketri.entity.user.model.User;

import java.util.Optional;
import java.util.UUID;

public interface UserService {

    Optional<User> findById(UUID uuid);
    Optional<User> findByName(String name);
    User save(User e);
}
