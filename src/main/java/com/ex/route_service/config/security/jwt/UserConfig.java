package com.ex.route_service.config.security.jwt;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

/**
 * Тестовая конфигурация пользователей для Spring Security.
 * Создаёт два пользователя в памяти с ролями "ADMIN" и "USER".
 */
@Configuration
public class UserConfig {

    @Bean
    public UserDetailsService userDetailsService() {
        var userAdmin = User.builder()
                .username("admin")
                .password(passwordEncoder().encode("password"))
                .roles("ADMIN")
                .build();

        var userUser = User.builder()
                .username("user")
                .password(passwordEncoder().encode("password"))
                .roles("USER")
                .build();

        return new InMemoryUserDetailsManager(userAdmin, userUser);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}