package com.ex.route_service.config.security;

import com.ex.route_service.config.security.jwt.JwtFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Конфигурация безопасности Spring Security.
 * <p>
 * Настраивает фильтрацию HTTP-запросов, интеграцию JWT-фильтра и аутентификацию с использованием
 * {@link UserDetailsService} и {@link PasswordEncoder}.
 * Также включает поддержку аннотаций безопасности {@code @PreAuthorize} и других.
 * </p>
 */
@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtFilter jwtFilter;
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;


    /**
     * Конфигурирует цепочку фильтров безопасности для HTTP-запросов.
     * Добавляет кастомный JWT-фильтр перед стандартным {@link UsernamePasswordAuthenticationFilter}.
     *
     * @param http объект для конфигурации безопасности HTTP
     * @return настроенная цепочка фильтров безопасности {@link SecurityFilterChain}
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                                .requestMatchers("/auth/**").permitAll()
//                        разрешил все
                                .anyRequest().permitAll()
                )
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    /**
     * Создаёт и конфигурирует {@link AuthenticationManager} с использованием
     * {@link DaoAuthenticationProvider}, который применяет {@link UserDetailsService} и {@link PasswordEncoder}
     * для проверки пользователей и паролей.
     *
     * @return настроенный менеджер аутентификации {@link AuthenticationManager}
     */
    @Bean
    public AuthenticationManager authenticationManager() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder);
        return new ProviderManager(provider);
    }
}