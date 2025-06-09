package com.platform.prism;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Main application class for PRISM.
 * This class serves as the entry point for the Spring Boot application.
 */
@SpringBootApplication
@EnableJpaRepositories
public class PrismApplication {

    /**
     * Main method to start the Spring Boot application.
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(PrismApplication.class, args);
    }

}
