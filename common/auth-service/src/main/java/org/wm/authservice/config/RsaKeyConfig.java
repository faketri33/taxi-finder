package org.wm.authservice.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import java.security.KeyFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;


/**
 * Rsa key configuration.
 * read keys from files and generated RSAPrivateKey and RSAPublicKey
 *
 */
@Configuration
public class RsaKeyConfig {

    @Value("${jwts.keys.private}")
    private Resource privateKeyResource;
    @Value("${jwts.keys.public}")
    private Resource publicKeyResource;


    /**
     * Generated RSAPrivateKey from file in storage.
     *
     */
    @Bean
    public RSAPrivateKey privateKey() throws Exception {
        String pem = new String(privateKeyResource.getInputStream().readAllBytes())
                .replace("-----BEGIN PRIVATE KEY-----", "")
                .replace("-----END PRIVATE KEY-----", "")
                .replaceAll("\\s", "");

        byte[] decoded = Base64.getDecoder().decode(pem);
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(decoded);

        return (RSAPrivateKey) KeyFactory.getInstance("RSA").generatePrivate(spec);
    }

    /**
     * Generated RSAPublicKey from file in storage.
     *
     */
    @Bean
    public RSAPublicKey publicKey() throws Exception {
        String pem = new String(publicKeyResource.getInputStream().readAllBytes())
                .replace("-----BEGIN PUBLIC KEY-----", "")
                .replace("-----END PUBLIC KEY-----", "")
                .replaceAll("\\s", "");

        byte[] decoded = Base64.getDecoder().decode(pem);
        X509EncodedKeySpec spec = new X509EncodedKeySpec(decoded);

        return (RSAPublicKey) KeyFactory.getInstance("RSA").generatePublic(spec);
    }
}
