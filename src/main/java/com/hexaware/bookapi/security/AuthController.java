package com.hexaware.bookapi.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody LoginRequest req) {
        Authentication auth = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(req.getUsername(), req.getPassword()));
        String token = tokenProvider.generateToken(auth);
        return Map.of("token", token);
    }
}
