package org.faketri.usecase.user;

import org.faketri.entity.user.gateway.UserRepository;
import org.faketri.entity.user.model.User;
import org.faketri.infrastructure.user.gateway.UserService;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<User> findById(UUID uuid) {
        return userRepository.findById(uuid);
    }

    @Override
    public Optional<User> findByName(String name) {
        return Optional.empty();
    }

    @Override
    public User save(User e) {
        return userRepository.save(e);
    }
}
