package org.wm.authservice.usecase;

import org.wm.authservice.domain.entity.CustomUserDetails;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface JwtService {

    String[] generateJwt(CustomUserDetails userDetails);

    Boolean validate(String token);

    UUID extractUserId(String token);
    List<String> extractUserRole(String token);

    Map<String, Object> publicKeys();
}
