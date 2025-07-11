package com.hexaware.bookapi.security;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private CustomUserDetailsService uds; // Assuming CustomUserDetailsService

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse resp, FilterChain filterChain)
            throws ServletException, IOException {

        // --- START OF CRUCIAL ADDITION ---
        // Do NOT process JWT for login, registration, or other public endpoints
        // These paths should be explicitly allowed in SecurityConfig with .permitAll()
        if (req.getServletPath().equals("/api/auth/login") || req.getServletPath().equals("/api/auth/register")) {
            filterChain.doFilter(req, resp);
            return; // IMPORTANT: Skip the rest of the filter logic for these paths
        }
        // --- END OF CRUCIAL ADDITION ---


        String header = req.getHeader("Authorization");
        String token = null;

        // If a token is present, attempt to validate it
        if (header != null && header.startsWith("Bearer ")) {
            token = header.substring(7); // Extract the token part
        }

        // Only proceed with token validation if a token was found AND it's valid
        if (token != null && tokenProvider.validate(token)) {
            String username = tokenProvider.getUsername(token); // Get username from token
            // Load user details
            UserDetails ud = uds.loadUserByUsername(username);
            // Create authentication object
            UsernamePasswordAuthenticationToken auth =
                    new UsernamePasswordAuthenticationToken(ud, null, ud.getAuthorities());
            // Set authentication in SecurityContextHolder
            SecurityContextHolder.getContext().setAuthentication(auth);
        }

        // Continue the filter chain
        filterChain.doFilter(req, resp);
    }
}