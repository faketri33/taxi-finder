package org.wm.authservice.infra.persistence.repo;

import org.wm.authservice.infra.persistence.entity.Users;

import java.util.Optional;

public interface UsersRepository {

    Optional<Users> findByUsername(String username);
    Users save(Users us);
}
