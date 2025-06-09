package com.platform.prism.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.platform.prism.dto.MedicationDto;
import com.platform.prism.model.Medication;
import com.platform.prism.service.MedicationService;
import com.platform.prism.util.mapper.MedicationMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class MedicationControllerTest {

    private MockMvc mockMvc;

    @Mock
    private MedicationService medicationService;

    @Mock
    private MedicationMapper medicationMapper;

    @InjectMocks
    private MedicationController medicationController;

    private ObjectMapper objectMapper;
    private Medication medication;
    private MedicationDto dto;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(medicationController).build();
        objectMapper = new ObjectMapper();

        medication = Medication.builder()
                .id(1L)
                .proprietaryName("Tylenol")
                .ndcPackageCode("50580-139-04")
                .build();

        dto = MedicationDto.builder()
                .id(1L)
                .proprietaryName("Tylenol")
                .ndcPackageCode("50580-139-04")
                .build();
    }

    @Test
    void shouldReturnMedicationById_whenExists() throws Exception {
        when(medicationService.getMedicationById(1L)).thenReturn(dto);

        mockMvc.perform(get("/medications/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.proprietaryName").value("Tylenol"))
                .andExpect(jsonPath("$.ndcPackageCode").value("50580-139-04"));
    }

    @Test
    void shouldReturnNotFound_whenMedicationDoesNotExist() throws Exception {
        when(medicationService.getMedicationById(99L))
                .thenThrow(new RuntimeException("Medication not found"));

        mockMvc.perform(get("/medications/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldReturnAllMedications() throws Exception {
        Medication medication2 = Medication.builder()
                .id(2L)
                .proprietaryName("Aspirin")
                .ndcPackageCode("12345-6789")
                .build();

        MedicationDto dto2 = MedicationDto.builder()
                .id(2L)
                .proprietaryName("Aspirin")
                .ndcPackageCode("12345-6789")
                .build();

        when(medicationService.getAllMedications()).thenReturn(List.of(medication, medication2));
        when(medicationMapper.toDto(medication)).thenReturn(dto);
        when(medicationMapper.toDto(medication2)).thenReturn(dto2);

        mockMvc.perform(get("/medications"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].proprietaryName").value("Tylenol"))
                .andExpect(jsonPath("$[1].proprietaryName").value("Aspirin"));
    }

    @Test
    void shouldCreateMedication() throws Exception {
        Medication saved = Medication.builder().id(1L).proprietaryName("Tylenol").ndcPackageCode("50580-139-04").build();

        when(medicationMapper.toEntity(any())).thenReturn(medication);
        when(medicationService.saveMedication(any())).thenReturn(saved);
        when(medicationMapper.toDto(saved)).thenReturn(dto);

        mockMvc.perform(post("/medications")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.proprietaryName").value("Tylenol"));
    }

    @Test
    void shouldUpdateMedication_whenExists() throws Exception {
        when(medicationService.updateMedication(eq(1L), any())).thenReturn(dto);

        mockMvc.perform(put("/medications/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.proprietaryName").value("Tylenol"));
    }

    @Test
    void shouldReturnNotFound_whenUpdatingNonExistentMedication() throws Exception {
        when(medicationService.updateMedication(eq(99L), any()))
                .thenThrow(new RuntimeException("Medication not found"));

        mockMvc.perform(put("/medications/99")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldDeleteMedication_whenExists() throws Exception {
        doNothing().when(medicationService).deleteMedication(1L);

        mockMvc.perform(delete("/medications/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldReturnNotFound_whenDeletingNonExistentMedication() throws Exception {
        Mockito.doThrow(new RuntimeException("Medication not found"))
                .when(medicationService).deleteMedication(99L);

        mockMvc.perform(delete("/medications/99"))
                .andExpect(status().isNotFound());
    }
}
