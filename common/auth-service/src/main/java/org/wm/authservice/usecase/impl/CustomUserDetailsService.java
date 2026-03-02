package org.wm.authservice.usecase.impl;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.wm.authservice.domain.mapper.UsersMapper;
import org.wm.authservice.infra.persistence.entity.UsersEntity;
import org.wm.authservice.infra.persistence.repo.UsersRepository;


@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UsersRepository usersRepository;
    private final UsersMapper usersMapper;

    public CustomUserDetailsService(UsersRepository usersRepository, UsersMapper usersMapper) {
        this.usersRepository = usersRepository;
        this.usersMapper = usersMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UsersEntity user = usersRepository
                .findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("User with name %s not found", username)));

        return usersMapper.toUserDetails(user);
    }
}
