package org.faketri.infrastructure.user.controller;


import org.faketri.infrastructure.user.gateway.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/catalog")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    public ResponseEntity<String> test(){
        return ResponseEntity.ok().body("Hello");
    }
}
