package org.wm.authservice.api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.wm.authservice.api.dto.UserAuthRequest;
import org.wm.authservice.usecase.auth.AuthService;

@RequestMapping("/api/v1/auth")
public class JwtController {

    private final AuthService authService;

    public JwtController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public void register(@RequestBody UserAuthRequest urr) {
        authService.register(urr);
    }

    @PostMapping("/login")
    public void login(@RequestBody UserAuthRequest urr) {
        authService.login(urr);
    }

    @GetMapping("/token-refresh")
    public void refresh(@RequestBody String token) {
        authService.tokenUpdate(token);
    }

    @GetMapping("/.well-known/")
    public void publicKeys() {
        authService.publicKeys();
    }
}
