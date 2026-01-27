package org.wm.authservice.usecase.auth;

import org.springframework.stereotype.Service;
import org.wm.authservice.api.dto.UserAuthRequest;
import org.wm.authservice.infra.persistence.repo.UsersRepository;
import org.wm.authservice.usecase.jwt.JwtService;

@Service
public class AuthServiceImpl implements AuthService {

    private final UsersRepository usersRepository;
    private final JwtService jwtService;

    public AuthServiceImpl(JwtService jwtService, UsersRepository usersRepository){
        this.jwtService = jwtService;
        this.usersRepository = usersRepository;
    }

    @Override
    public String register(UserAuthRequest urr) {
        throw new UnsupportedOperationException("Unimplemented method 'register'");
    }

    @Override
    public String login(UserAuthRequest urr) {
        throw new UnsupportedOperationException("Unimplemented method 'login'");
    }

    @Override
    public String tokenUpdate(String refresh) {
        throw new UnsupportedOperationException("Unimplemented method 'tokenUpdate'");
    }

    @Override
    public String publicKeys() {
        throw new UnsupportedOperationException("Unimplemented method 'publicKeys'");
    }


}
