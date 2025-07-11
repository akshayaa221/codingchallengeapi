package com.hexaware.bookapi.security;

import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class CustomUserDetailsService implements UserDetailsService {
	
    private final Map<String, UserDetails> users = new HashMap<>();

    
    private final BCryptPasswordEncoder passwordEncoder;

    
    @Autowired
    public CustomUserDetailsService(BCryptPasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder; 

       
        UserDetails user = User.builder()
                .username("user")
                .password(passwordEncoder.encode("password")) 
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
