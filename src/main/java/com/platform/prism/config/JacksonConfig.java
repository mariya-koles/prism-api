package com.platform.prism.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.hibernate6.Hibernate6Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class that customizes the default Jackson {@link ObjectMapper}
 * used for JSON serialization and deserialization throughout the application.
 * This configuration registers two important modules:
 * {@link Hibernate6Module} - to handle serialization of Hibernate-managed entities
 *       and prevent lazy-loading during JSON serialization.
 * {@link JavaTimeModule} - to enable proper handling of Java 8 date and time types
 *       like {@code LocalDate} and {@code LocalDateTime}.
 * By providing a custom {@code ObjectMapper} bean, this configuration
 * overrides Spring Boot’s default setup, ensuring consistent behavior across
 * the application when converting Java objects to/from JSON.
 *
 * @author mariya-koles
 */
@Configuration
public class JacksonConfig {

    /**
     * Creates and configures a customized Jackson ObjectMapper.
     * Configurations:
     * - Disables lazy-loading for Hibernate-managed entities to prevent unintended DB access.
     * - Adds support for Java 8 date/time API types.
     * @return a configured ObjectMapper instance.
     */
    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();

        // Configure Hibernate module
        Hibernate6Module hibernate6Module = new Hibernate6Module();
        hibernate6Module.configure(Hibernate6Module.Feature.FORCE_LAZY_LOADING, false);
        mapper.registerModule(hibernate6Module);

        // Add Java 8 date/time support
        mapper.registerModule(new JavaTimeModule());

        // This ensures LocalDate will serialize as "2025-06-08"
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        return mapper;
    }
}