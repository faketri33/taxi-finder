package org.wm.authservice.usecase.impl;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import org.wm.authservice.domain.entity.CustomUserDetails;
import org.wm.authservice.usecase.JwtService;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.text.ParseException;
import java.util.*;


/**
 * Main JwtService generated tokens for users.
 */
@Service
public class JwtServiceImpl implements JwtService {

    private enum JwtType {
        ACCESS,
        REFRESH
    }

    private static final Logger log = LoggerFactory.getLogger(JwtServiceImpl.class);

    private static final String KEY_ID = "auth-key-1";
    private static final String ISSUER = "auth-service";
    private static final String AUDIENCE = "taxi-finder";

    private final RSAPrivateKey privateKey;
    private final RSAPublicKey publicKey;

    private final long privateKeyTtlMs;
    private final long publicKeyTtlMs;

    public JwtServiceImpl(RSAPrivateKey privateKey, RSAPublicKey publicKey,
                          @Value("${jwts.keys.private.lifetime}") long privateKeyTtlMs,
                          @Value("${jwts.keys.public.lifetime}") long publicKeyTtlMs) {
        this.privateKey = privateKey;
        this.publicKey = publicKey;
        this.privateKeyTtlMs = privateKeyTtlMs;
        this.publicKeyTtlMs = publicKeyTtlMs;
    }


    /**
     * Generating a JWT key containing user information. To access possible resources
     *
     * @param userDetails default spring secure type for users
     * @return An array of strings containing key-key pairs. (access && refresh)
     *
     */
    @Override
    public String[] generateJwt(CustomUserDetails userDetails) {
        try {
            return new String[]{
                    serializedJwt(userDetails, privateKeyTtlMs, JwtType.ACCESS),
                    serializedJwt(userDetails, publicKeyTtlMs, JwtType.REFRESH)
            };
        } catch (JOSEException ex) {
            log.error(ex.getLocalizedMessage());
            throw new RuntimeException("Cannot create jwt token");
        }
    }


    /**
     * Public key for other service
     */
    @Override
    public Map<String, Object> publicKeys() {
        RSAKey rsaKey = new RSAKey.Builder(publicKey)
                .keyID(KEY_ID)
                .algorithm(JWSAlgorithm.RS256)
                .build();

        return new JWKSet(rsaKey).toJSONObject();
    }


    public UUID extractUserId(String token) {
        try {
            String uuid = SignedJWT.parse(token).getJWTClaimsSet().getSubject();
            return UUID.fromString(uuid);
        } catch (ParseException ex) {
            log.error(ex.getLocalizedMessage());
        }
        return new UUID(0, 0);
    }

    @Override
    public List<String> extractUserRole(String token) {
        try {
            return (List<String>) SignedJWT.parse(token).getJWTClaimsSet().getClaim("roles");
        }catch (ParseException ex){
            log.error(ex.getLocalizedMessage());
        }
        return List.of();
    }


    public Boolean validate(String token) {
        try {
            SignedJWT jwts = SignedJWT.parse(token);
            JWSVerifier verifier = new RSASSAVerifier(publicKey);

            if ( ! jwts.verify(verifier)) return false;

            Date expirationTime = jwts.getJWTClaimsSet().getExpirationTime();

            return expirationTime != null && expirationTime.after(new Date());
        } catch (ParseException | JOSEException ex) {
            log.error(ex.getLocalizedMessage());
        }
        return false;
    }


    /**
     * generate claims for token.
     * Contains roles user, time expiration, issuer and audience.
     *
     * @param userDetails information about users who have account in service. (Default spring secure)
     * @param type        access || refresh
     *
     *
     */
    private JWTClaimsSet generateClaims(CustomUserDetails userDetails, long ttlMs, JwtType type) {

        var now = new Date();

        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        return new JWTClaimsSet.Builder()
                .subject(userDetails.getId().toString())
                .issuer(ISSUER)
                .audience(AUDIENCE)
                .claim("roles", roles)
                .claim("typ", type.name())
                .issueTime(now)
                .expirationTime(new Date(now.getTime() + ttlMs))
                .jwtID(UUID.randomUUID().toString())
                .build();
    }

    /**
     * Generating a JWT key containing user information. To access possible resources
     *
     * @param userDetails default spring secure type for users
     * @param ttlMs       token lifetime
     * @param type        (access || refresh)
     *
     */
    private String serializedJwt(CustomUserDetails userDetails, long ttlMs, JwtType type) throws JOSEException {
        JWSSigner signer = new RSASSASigner(privateKey);

        SignedJWT jwt = new SignedJWT(
                new JWSHeader.Builder(JWSAlgorithm.RS256)
                        .keyID(KEY_ID)
                        .type(JOSEObjectType.JWT)
                        .build(),
                generateClaims(userDetails, ttlMs, type)
        );

        jwt.sign(signer);

        return jwt.serialize();
    }
}
