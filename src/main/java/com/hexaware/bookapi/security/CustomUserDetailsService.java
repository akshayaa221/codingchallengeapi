package com.hexaware.bookapi.security;

import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.*;

// Import for @Autowired
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final Map<String, UserDetails> users = new HashMap<>();

    // Autowire the BCryptPasswordEncoder bean
    private final BCryptPasswordEncoder passwordEncoder;

    // Use constructor injection for BCryptPasswordEncoder
    @Autowired
    public CustomUserDetailsService(BCryptPasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder; // Assign the injected encoder

        // Encode the password using the injected passwordEncoder
        UserDetails user = User.builder()
                .username("user")
                .password(passwordEncoder.encode("password")) // Use the injected encoder
                .roles("USER")
                .build();
        users.put("user", user);
    }

    @Override
    public UserDetails loadUserByUsername(String u) {
        if (users.containsKey(u)) {
            return users.get(u);
        }
        throw new UsernameNotFoundException("User not found: " + u);
    }
}
