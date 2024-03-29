package com.api.login.auth.controllers;

import com.api.login.auth.dto.LoginRequestDTO;
import com.api.login.auth.dto.RegisterRequestDTO;
import com.api.login.auth.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginRequestDTO body) throws Exception {
        return ResponseEntity.ok().body(userService.login(body));
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody RegisterRequestDTO body) throws Exception {
        return ResponseEntity.ok().body(userService.register(body));
    }
}
