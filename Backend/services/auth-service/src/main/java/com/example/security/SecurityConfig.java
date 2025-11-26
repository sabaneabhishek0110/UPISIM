package com.example.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {
    public final TokenService tokenService;

    public SecurityConfig(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
        .csrf(csrf ->csrf.disable())
        .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/User/register",
                        "/api/User/login",
                        "/otp/**",
                        "/api/User/getAllUsers")
                .permitAll() // public endpoints
                .anyRequest().authenticated() // protect all other endpoints
        )
        .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // JWT, no session
        );

        http.addFilterBefore(new JwtAuthFilter(tokenService),
                UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
