package com.example.harbourquests.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

	private final String prefix = "/api/v1";

	@Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.
                authorizeHttpRequests(authorizationManagerRequestMatcherRegistry ->
                authorizationManagerRequestMatcherRegistry
                        .requestMatchers(prefix + "/courier/**").hasAnyRole("COURIER", "ADMIN")
						.requestMatchers(HttpMethod.GET, prefix + "/order/**").hasAnyRole("COURIER", "ADMIN")
						.requestMatchers(HttpMethod.PUT, prefix + "/order/**").hasAnyRole("COURIER", "ADMIN")
						.requestMatchers(HttpMethod.POST, prefix + "/order/**").hasAnyRole("ADMIN")
						.requestMatchers(HttpMethod.GET, prefix + "/quest/**").hasAnyRole("COURIER", "ADMIN")
						.requestMatchers(HttpMethod.POST, prefix + "/quest/**").hasAnyRole("ADMIN")
						.requestMatchers(HttpMethod.PUT, prefix + "/quest/**").hasAnyRole("ADMIN")
                        .anyRequest().authenticated())
                .httpBasic(Customizer.withDefaults())
                .sessionManagement(httpSecuritySessionManagementConfigurer -> httpSecuritySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .csrf(csrf -> csrf.disable());


        return http.build();
    }
}
