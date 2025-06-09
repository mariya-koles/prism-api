package com.platform.prism.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.platform.prism.dto.PatientDto;
import com.platform.prism.model.Patient;
import com.platform.prism.service.PatientService;
import com.platform.prism.util.PatientMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.BDDMockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PatientController.class)
@AutoConfigureMockMvc
@WithMockUser
@ActiveProfiles("test")
class PatientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PatientService patientService;

    @MockBean
    private PatientMapper patientMapper;

    @Autowired
    private ObjectMapper objectMapper;

    private PatientDto dto;
    private Patient entity;

    @BeforeEach
    void setUp() {
        dto = PatientDto.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .age(45)
                .dateOfBirth(LocalDate.of(1980, 5, 15))
                .email("john.doe@example.com")
                .build();

        entity = Patient.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .age(45)
                .dateOfBirth(LocalDate.of(1980, 5, 15))
                .email("john.doe@example.com")
                .build();
    }

    @Test
    void shouldReturnPatientById() throws Exception {
        given(patientService.getPatientById(1L)).willReturn(dto);

        mockMvc.perform(get("/patients/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("John"));
    }

    @Test
    void shouldReturnNotFoundIfPatientMissing() throws Exception {
        given(patientService.getPatientById(99L)).willThrow(new RuntimeException());

        mockMvc.perform(get("/patients/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldReturnAllPatients() throws Exception {
        given(patientService.getAllPatients()).willReturn(List.of(entity));
        given(patientMapper.toDto(entity)).willReturn(dto);

        mockMvc.perform(get("/patients"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].firstName").value("John"));
    }

    @Test
    void shouldCreatePatient() throws Exception {
        given(patientMapper.toEntity(any())).willReturn(entity);
        given(patientService.savePatient(any())).willReturn(entity);
        given(patientMapper.toDto(entity)).willReturn(dto);

        mockMvc.perform(post("/patients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)).with(csrf()))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName").value("John"));
    }

    @Test
    void shouldUpdatePatient() throws Exception {
        given(patientService.updatePatient(eq(1L), any())).willReturn(dto);

        mockMvc.perform(put("/patients/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)).with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("John"));
    }

    @Test
    void shouldReturnNotFoundWhenUpdatingMissingPatient() throws Exception {
        given(patientService.updatePatient(eq(99L), any())).willThrow(new RuntimeException());

        mockMvc.perform(put("/patients/99")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldDeletePatient() throws Exception {
        willDoNothing().given(patientService).deletePatient(1L);

        mockMvc.perform(delete("/patients/1").with(csrf()))
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldReturnNotFoundOnDeleteIfMissing() throws Exception {
        willThrow(new RuntimeException()).given(patientService).deletePatient(99L);

        mockMvc.perform(delete("/patients/99").with(csrf()))
                .andExpect(status().isNotFound());
    }
}
