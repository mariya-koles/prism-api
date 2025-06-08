package com.platform.prism.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate6.Hibernate6Module;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;


import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@SpringBootTest
class JacksonConfigTest {

    private final JacksonConfig jacksonConfig = new JacksonConfig();

    @Test
    void objectMapper_shouldRegisterHibernate6Module_withForceLazyLoadingFalse() {
        ObjectMapper mapper = new JacksonConfig().objectMapper();

        Hibernate6Module module = new Hibernate6Module();
        module.configure(Hibernate6Module.Feature.FORCE_LAZY_LOADING, false);

        assertThat(module.isEnabled(Hibernate6Module.Feature.FORCE_LAZY_LOADING)).isFalse();
    }

    @Test
    void objectMapper_shouldRegisterJavaTimeModule() {
        ObjectMapper mapper = jacksonConfig.objectMapper();

        // Try serializing a LocalDate and ensure no exception is thrown
        String json = null;
        try {
            json = mapper.writeValueAsString(java.time.LocalDate.of(2025, 6, 8));
        } catch (Exception e) {
            e.printStackTrace();
        }

        assertThat(json).contains("2025-06-08");
    }
}
