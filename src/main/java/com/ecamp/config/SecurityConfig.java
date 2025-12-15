package com.ecamp.config;

import org.springframework.context.annotation.*;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.*;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(cs -> cs.disable())
                .formLogin(fl -> fl.disable())
                .httpBasic(b -> b.disable())
                .authorizeHttpRequests(auth -> auth

                        // Parent register/login pages & endpoints
                        .requestMatchers(
                                "/parent-register.html", "/parent-login.html", "/parent-dashboard.html", "/parent-children.html", "/parent-registrations.html",
                                "/api/parents/register", "/api/parents/login",
                                "/admin-login.html", "/owner-login.html",
                                "/api/auth/admin/login", "/api/auth/owner/login",
                                "/", "/index.html", "/css/**", "/js/**", "/assets/**",
                                "login-child.html", "/api/auth/child/login", "/favicon.ico",
                                "/api/**"
                        ).permitAll()

                        // OPEN children GET so Manage My Children works
                        .requestMatchers(HttpMethod.GET, "/api/children", "/api/children/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/children").permitAll()

                        // OPEN registrations GET/POST if you list them
                        .requestMatchers(HttpMethod.GET,  "/api/registrations", "/api/registrations/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/registrations").permitAll()

                        // OPEN camps/trips/instructors/etc reads for Owner UI
                        .requestMatchers(HttpMethod.GET,
                                "/api/camps", "/api/camps/**",
                                "/api/instructors", "/api/instructors/**",
                                "/api/groups", "/api/groups/**",
                                "/api/activities", "/api/activities/**",
                                "/api/trips", "/api/trips/**", "/api/parents",
                                "/api/camps/child/**", "/api/children/**"
                        ).permitAll()

                        // Owner-only writes
                        .requestMatchers(HttpMethod.POST,   "/api/schedules/**", "/api/scores/**").hasRole("OWNER")
                        .requestMatchers(HttpMethod.PUT,    "/api/schedules/**", "/api/scores/**").hasRole("OWNER")
                        .requestMatchers(HttpMethod.DELETE, "/api/schedules/**", "/api/scores/**").hasRole("OWNER")

                        // Admin-only writes
                        .requestMatchers(HttpMethod.POST,   "/api/camps/**", "/api/instructors/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT,    "/api/camps/**", "/api/instructors/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/camps/**", "/api/instructors/**").hasRole("ADMIN")

                        // All other endpoints require authentication
                        .anyRequest().authenticated()
                );

        return http.build();
    }

    // Expose AuthenticationManager for other controllers (if needed)
    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration config
    ) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
