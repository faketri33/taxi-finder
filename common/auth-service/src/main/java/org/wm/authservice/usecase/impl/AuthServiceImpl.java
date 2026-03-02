package org.wm.authservice.usecase.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.wm.authservice.api.dto.request.UserAuthRequest;
import org.wm.authservice.domain.entity.CustomUserDetails;
import org.wm.authservice.domain.entity.UsersDomain;
import org.wm.authservice.domain.mapper.UsersMapper;
import org.wm.authservice.infra.persistence.entity.Roles;
import org.wm.authservice.usecase.AuthService;
import org.wm.authservice.usecase.UserService;
import org.wm.authservice.usecase.JwtService;

import java.util.Map;
import java.util.UUID;


@Service
public class AuthServiceImpl implements AuthService {

    private static final Logger log = LoggerFactory.getLogger(AuthServiceImpl.class);

    private final UserService userService;
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    private final UsersMapper usersMapper;

    public AuthServiceImpl(JwtService jwtService, UserService userService, UserDetailsService userDetailsService, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, UsersMapper usersMapper) {
        this.jwtService = jwtService;
        this.userService = userService;
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.usersMapper = usersMapper;
    }

    @Override
    public String[] register(UserAuthRequest urr) {

        UsersDomain ue = new UsersDomain();

        ue.setUsername(urr.username());
        ue.setPassword(passwordEncoder.encode(urr.password()));
        ue.getRoles().add(Roles.DEFAULT);

        CustomUserDetails cud = (CustomUserDetails) usersMapper.toUserDetails(userService.save(ue));

        return jwtService.generateJwt(cud);
    }

    @Override
    public String[] login(UserAuthRequest urr) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            urr.username(),
                            urr.password()
                    ));
        } catch (BadCredentialsException ex) {
            log.error("Not valid password from user with login {}", urr.username());
            throw new BadCredentialsException("Invalidate login data");
        }

        final CustomUserDetails cud = (CustomUserDetails) userDetailsService.loadUserByUsername(urr.username());

        return jwtService.generateJwt(cud);
    }

    @Override
    public String[] tokenUpdate(String refresh) {
        UUID userID = jwtService.extractUserId(refresh);
        UsersDomain ud = userService.get(userID)
                .orElseThrow(() -> new RuntimeException("Users not exists"));

        return jwtService.generateJwt((CustomUserDetails) usersMapper.toUserDetails(ud));
    }

    @Override
    public Map<String, Object> publicKeys() {
        return jwtService.publicKeys();
    }


}
