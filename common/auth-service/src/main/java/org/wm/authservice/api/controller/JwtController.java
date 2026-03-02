package org.wm.authservice.api.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.wm.authservice.api.dto.response.TokenResponse;
import org.wm.authservice.api.dto.request.UserAuthRequest;
import org.wm.authservice.usecase.AuthService;

import java.time.Duration;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
public class JwtController {

    private final AuthService authService;

    public JwtController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<TokenResponse> register(@RequestBody UserAuthRequest urr, HttpServletResponse response) {

        String[] tokens = authService.register(urr);

        addCookie(tokens[1], response);

        return ResponseEntity.ok(new TokenResponse(tokens[0]));
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestBody UserAuthRequest urr, HttpServletResponse response) {
        String[] tokens = authService.login(urr);

        addCookie(tokens[1], response);

        return ResponseEntity.ok(new TokenResponse(tokens[0]));
    }

    @GetMapping("/token-refresh")
    public ResponseEntity<TokenResponse> refresh(@CookieValue("refresh_token") String refreshToken,
                            HttpServletResponse response) {
        var tokens = authService.tokenUpdate(refreshToken);

        addCookie(tokens[1], response);

        return ResponseEntity.ok(new TokenResponse(tokens[0]));
    }

    @GetMapping("/.well-known/")
    public Map<String, Object> publicKeys() {
        return authService.publicKeys();
    }

    private void addCookie(String token, HttpServletResponse response){
        var refreshCookie = ResponseCookie.from("refresh_token", token)
                .httpOnly(true)
                .secure(true)
                .sameSite("Strict")
                .path("/api/v1/auth")
                .maxAge(Duration.ofDays(7))
                .build();

        response.addHeader(HttpHeaders.SET_COOKIE, refreshCookie.toString());
    }
}
