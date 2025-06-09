package com.platform.prism.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class SecurityConfigTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void swaggerEndpointShouldBeBlockedOutsideDev() throws Exception {
        mockMvc.perform(get("/swagger-ui/index.html"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void swaggerShouldBeBlockedOutsideDev() throws Exception {
        mockMvc.perform(get("/v3/api-docs"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void securedEndpointShouldReturnUnauthorizedWithoutLogin() throws Exception {
        mockMvc.perform(get("/patients"))
                .andExpect(status().isUnauthorized()); // or Forbidden if CSRF or roles apply
    }

    @Test
    void csrfShouldBeDisabledForPost() throws Exception {
        mockMvc.perform(post("/patients").with(csrf()))
                .andExpect(status().isUnauthorized()); // You’re not logged in, but CSRF doesn’t block
    }

}
