package org.wm.authservice.usecase.jwt;

import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {

    String generateJwtAccess(UserDetails userDetails);
    String generateJwtRefresh(UserDetails userDetails);
    String publicKeys();
}
