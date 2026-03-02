package org.wm.authservice.usecase.impl;

import org.springframework.stereotype.Service;
import org.wm.authservice.domain.entity.UsersDomain;
import org.wm.authservice.domain.mapper.UsersMapper;
import org.wm.authservice.infra.persistence.entity.UsersEntity;
import org.wm.authservice.infra.persistence.repo.UsersRepository;
import org.wm.authservice.usecase.UserService;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    private final UsersRepository usersRepository;
    private final UsersMapper usersMapper;

    public UserServiceImpl(UsersRepository usersRepository, UsersMapper usersMapper) {
        this.usersRepository = usersRepository;
        this.usersMapper = usersMapper;
    }

    @Override
    public Optional<UsersDomain> get(UUID id) {
        return Optional.empty();
    }

    @Override
    public UsersDomain save(UsersDomain ud) {
        UsersEntity ue = usersMapper.toEntity(ud);
        ue = usersRepository.save(ue);
        return usersMapper.toDomain(ue);
    }

    @Override
    public UsersDomain save(UsersEntity ue) {
        return usersMapper.toDomain(usersRepository.save(ue));
    }
}
