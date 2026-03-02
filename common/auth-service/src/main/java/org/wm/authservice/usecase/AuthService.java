package org.wm.authservice.usecase;

import org.wm.authservice.api.dto.request.UserAuthRequest;

import java.util.Map;

public interface AuthService {

    String[] register(UserAuthRequest urr);

    String[] login(UserAuthRequest urr);

    String[] tokenUpdate(String refresh);

    Map<String, Object> publicKeys();
}
