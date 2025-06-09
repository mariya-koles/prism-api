package com.platform.prism.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.platform.prism.dto.PatientDto;
import com.platform.prism.model.Patient;
import com.platform.prism.service.PatientService;
import com.platform.prism.util.mapper.PatientMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class PatientControllerTest {

    private MockMvc mockMvc;

    @Mock
    private PatientService patientService;

    @Mock
    private PatientMapper patientMapper;

    @InjectMocks
    private PatientController patientController;

    private ObjectMapper objectMapper;
    private PatientDto dto;
    private Patient entity;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(patientController).build();
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

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
        given(patientService.getPatientById(99L)).willThrow(new RuntimeException("Patient not found"));

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
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName").value("John"));
    }

    @Test
    void shouldUpdatePatient() throws Exception {
        given(patientService.updatePatient(eq(1L), any())).willReturn(dto);

        mockMvc.perform(put("/patients/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("John"));
    }

    @Test
    void shouldReturnNotFoundWhenUpdatingMissingPatient() throws Exception {
        given(patientService.updatePatient(eq(99L), any())).willThrow(new RuntimeException("Patient not found"));

        mockMvc.perform(put("/patients/99")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldDeletePatient() throws Exception {
        willDoNothing().given(patientService).deletePatient(1L);

        mockMvc.perform(delete("/patients/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldReturnNotFoundOnDeleteIfMissing() throws Exception {
        willThrow(new RuntimeException("Patient not found")).given(patientService).deletePatient(99L);

        mockMvc.perform(delete("/patients/99"))
                .andExpect(status().isNotFound());
    }
}
