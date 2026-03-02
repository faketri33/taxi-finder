package org.wm.authservice.usecase.jwt;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.wm.authservice.domain.entity.CustomUserDetails;
import org.wm.authservice.usecase.JwtService;
import org.wm.authservice.usecase.impl.JwtServiceImpl;

import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.HashSet;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
class JwtServiceImplTest {

    @Mock
    private JwtService jwtService;

    @BeforeEach
    void setUp() throws Exception {
        var keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);
        var keyPair = keyPairGenerator.generateKeyPair();

        var privateKey = (RSAPrivateKey) keyPair.getPrivate();
        var publicKey  = (RSAPublicKey)  keyPair.getPublic();

        jwtService = new JwtServiceImpl(
                privateKey,
                publicKey,
                900_000,    // access ttl ms
                604_800_000  // refresh ttl ms
        );
    }


    @Test
    void generateJwt() {
        final UUID userID = UUID.randomUUID();
        final CustomUserDetails cud = new CustomUserDetails(userID, "asda", "secret", new HashSet<>());

        final var tokens = jwtService.generateJwt(cud);

        assertNotNull(tokens);
        assertTrue(jwtService.validate(tokens[0]));
        assertTrue(jwtService.validate(tokens[1]));

    }


    @Test
    void extractUserId() {
        final UUID userID = UUID.randomUUID();
        final CustomUserDetails cud = new CustomUserDetails(userID, "asda", "secret", new HashSet<>());

        final var tokens = jwtService.generateJwt(cud);

        var extractedId = jwtService.extractUserId(tokens[0]);

        assertEquals(userID, extractedId);

        extractedId = jwtService.extractUserId(tokens[1]);

        assertEquals(userID, extractedId);

    }

    /*
     * For this test to work correctly,
     * it is necessary to pass a TtlMS equal to zero,
     * so that our tokens are considered expired immediately after generation.
     *
     * in method setUp we need to change two last params in constructor JwtServiceImpl
     * */
    @Test
    void validate() {
        final UUID userID = UUID.randomUUID();
        final CustomUserDetails cud = new CustomUserDetails(userID, "asda", "secret", new HashSet<>());

        final var tokens = jwtService.generateJwt(cud);

        assertNotNull(tokens);
        assertFalse(jwtService.validate(tokens[0]));
        assertFalse(jwtService.validate(tokens[1]));

    }
}