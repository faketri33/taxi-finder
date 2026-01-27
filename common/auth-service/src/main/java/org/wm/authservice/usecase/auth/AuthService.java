package org.wm.authservice.usecase.auth;

import org.wm.authservice.api.dto.UserAuthRequest;

public interface AuthService {

    String register(UserAuthRequest urr);
    String login(UserAuthRequest urr);
    String tokenUpdate(String refresh);
    String publicKeys();
}
