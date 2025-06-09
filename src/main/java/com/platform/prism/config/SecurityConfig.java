package com.platform.prism.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    /**
     * Configures the HTTP security filter chain for the application.
     * This method defines which endpoints require authentication and which are publicly accessible,
     * sets up form-based login and HTTP Basic authentication, and disables CSRF protection for ease of testing.
     * Specifically:
     *     Permits unauthenticated access to Swagger UI and OpenAPI documentation endpoints.
     *     Requires authentication for all other endpoints.
     *     Enables Spring Security’s default login form and HTTP Basic authentication.
     *     Disables CSRF protection, which is typically required for non-browser clients like Postman or Swagger UI.
     *
     * @param http the {@link HttpSecurity} object used to customize web security behavior
     * @return the configured {@link SecurityFilterChain} bean
     * @throws Exception if an error occurs during security configuration
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, Environment env) throws Exception {
        boolean isDev = env.acceptsProfiles(Profiles.of("dev"));

        http
                .authorizeHttpRequests(auth -> {
                    if (isDev) {
                        auth.requestMatchers(
                                "/swagger-ui.html",
                                "/swagger-ui/**",
                                "/v3/api-docs/**",
                                "/swagger-resources/**",
                                "/webjars/**"
                        ).permitAll();
                    }
                    auth.anyRequest().authenticated();
                })
                .formLogin(Customizer.withDefaults())
                .httpBasic(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable);

        return http.build();
    }
}
