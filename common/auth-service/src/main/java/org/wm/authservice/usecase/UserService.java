package org.wm.authservice.usecase;

import org.wm.authservice.domain.entity.UsersDomain;
import org.wm.authservice.infra.persistence.entity.UsersEntity;

import java.util.Optional;
import java.util.UUID;

public interface UserService {

    Optional<UsersDomain> get(UUID id);

    UsersDomain save(UsersDomain ud);

    UsersDomain save(UsersEntity ue);

}
