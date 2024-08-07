package com.proyecto.saas.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.proyecto.saas.Jwt.JwtAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final AuthenticationProvider authProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(authRequest -> authRequest
                .requestMatchers(HttpMethod.GET).permitAll()
                .requestMatchers(HttpMethod.OPTIONS).permitAll()
                .requestMatchers("/auth/users**").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/users").permitAll()
                .requestMatchers(HttpMethod.PUT, "/api/users").permitAll()
                .requestMatchers("/api/users/encrypt-passwords").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/users/register").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/auth/login").permitAll()
                .requestMatchers("/api/users/register").permitAll()
                .requestMatchers("/api/users/**").permitAll()
                .requestMatchers("/api/auth/login").permitAll()
                .requestMatchers("/api/auth/register").permitAll()
                .requestMatchers("/api/auth/profile").permitAll()
                .requestMatchers("/api/courses/create").permitAll()
                .requestMatchers("/api/courses/**").permitAll()
                .requestMatchers("/api/images").permitAll()
                .requestMatchers("/api/images/**").permitAll()
                .requestMatchers("/api/subscriptions").permitAll()
                .requestMatchers("/api/subscriptions/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/courses/create").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/auth/register").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/auth/login").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/rdf/users").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/rdf/courses").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/rdf/subscriptions").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/rdf/query").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/rdf/all").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/subscriptions/consumer/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/subscriptions").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/subscriptions/create").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/users/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/users").permitAll()
                .requestMatchers(HttpMethod.PUT, "/api/users/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/subscriptions/create").permitAll() // Permitir acceso a este endpoint
                .anyRequest().authenticated())
            .sessionManagement(sessionManager -> sessionManager
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authenticationProvider(authProvider)
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
            .build();
    }

}
