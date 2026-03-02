package org.wm.authservice.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.wm.authservice.api.dto.request.UserAuthRequest;
import org.wm.authservice.config.WebSecure;
import org.wm.authservice.usecase.AuthService;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(JwtController.class)
@Import({WebSecure.class})
class JwtControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private AuthService authService;

    @Test
    void register() throws Exception {
        var request = new UserAuthRequest("user", "password");
        when(authService.register(any())).thenReturn(new String[]{"access", "refresh"});

        mockMvc.perform(post("/api/v1/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.access").value("access"))
                .andExpect(cookie().value("refresh_token", "refresh"))
                .andExpect(cookie().httpOnly("refresh_token",true));
    }

    @Test
    void login() throws Exception {

        var request = new UserAuthRequest("user", "passs");
        when(authService.login(any())).thenReturn(new String[] {"access", "refresh"});

        mockMvc.perform(post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.access").value("access"))
                .andExpect(cookie().value("refresh_token", "refresh"))
                .andExpect(cookie().httpOnly("refresh_token",true));
    }

    @Test
    void refresh() throws Exception {
        var cookie = new Cookie("refresh_token", "refresh");

        when(authService.tokenUpdate(any())).thenReturn(new String[]{"access", "refresh"});

        mockMvc.perform(get("/api/v1/auth/token-refresh")
                        .contentType(MediaType.APPLICATION_JSON)
                        .cookie(cookie))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.access").value("access"))
                .andExpect(cookie().value("refresh_token", "refresh"))
                .andExpect(cookie().httpOnly("refresh_token",true));
    }
}