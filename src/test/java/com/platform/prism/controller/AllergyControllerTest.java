package com.platform.prism.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.platform.prism.dto.AllergyDto;
import com.platform.prism.model.Allergy;
import com.platform.prism.service.AllergyService;
import com.platform.prism.util.mapper.AllergyMapper;
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
class AllergyControllerTest {

    private MockMvc mockMvc;

    @Mock
    private AllergyService allergyService;

    @Mock
    private AllergyMapper allergyMapper;

    @InjectMocks
    private AllergyController allergyController;

    private ObjectMapper objectMapper;
    private Allergy allergy;
    private AllergyDto dto;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(allergyController).build();
        objectMapper = new ObjectMapper();

        allergy = Allergy.builder()
                .id(1L)
                .allergyType("Drug")
                .drugName("Penicillin")
                .allergyName(null)
                .build();

        dto = AllergyDto.builder()
                .id(1L)
                .allergyType("Drug")
                .drugName("Penicillin")
                .allergyName(null)
                .build();
    }

    @Test
    void shouldReturnAllergyById_whenExists() throws Exception {
        when(allergyService.getAllergyById(1L)).thenReturn(dto);

        mockMvc.perform(get("/allergies/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.allergyType").value("Drug"))
                .andExpect(jsonPath("$.drugName").value("Penicillin"));
    }

    @Test
    void shouldReturnNotFound_whenAllergyDoesNotExist() throws Exception {
        when(allergyService.getAllergyById(99L))
                .thenThrow(new RuntimeException("Allergy not found"));

        mockMvc.perform(get("/allergies/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldReturnAllAllergies() throws Exception {
        Allergy allergy2 = Allergy.builder()
                .id(2L)
                .allergyType("Non-Drug")
                .allergyName("Shellfish")
                .drugName(null)
                .build();

        AllergyDto dto2 = AllergyDto.builder()
                .id(2L)
                .allergyType("Non-Drug")
                .allergyName("Shellfish")
                .drugName(null)
                .build();

        when(allergyService.getAllAllergies()).thenReturn(List.of(allergy, allergy2));
        when(allergyMapper.toDto(allergy)).thenReturn(dto);
        when(allergyMapper.toDto(allergy2)).thenReturn(dto2);

        mockMvc.perform(get("/allergies"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].allergyType").value("Drug"))
                .andExpect(jsonPath("$[1].allergyType").value("Non-Drug"));
    }

    @Test
    void shouldCreateAllergy() throws Exception {
        Allergy saved = Allergy.builder().id(1L).allergyType("Drug").drugName("Penicillin").build();

        when(allergyMapper.toEntity(any())).thenReturn(allergy);
        when(allergyService.saveAllergy(any())).thenReturn(saved);
        when(allergyMapper.toDto(saved)).thenReturn(dto);

        mockMvc.perform(post("/allergies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.allergyType").value("Drug"))
                .andExpect(jsonPath("$.drugName").value("Penicillin"));
    }

    @Test
    void shouldCreateNonDrugAllergy() throws Exception {
        AllergyDto nonDrugDto = AllergyDto.builder()
                .allergyType("Non-Drug")
                .allergyName("Peanuts")
                .drugName(null)
                .build();

        Allergy nonDrugAllergy = Allergy.builder()
                .allergyType("Non-Drug")
                .allergyName("Peanuts")
                .drugName(null)
                .build();

        Allergy saved = Allergy.builder().id(2L).allergyType("Non-Drug").allergyName("Peanuts").build();

        when(allergyMapper.toEntity(any())).thenReturn(nonDrugAllergy);
        when(allergyService.saveAllergy(any())).thenReturn(saved);
        when(allergyMapper.toDto(saved)).thenReturn(nonDrugDto);

        mockMvc.perform(post("/allergies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(nonDrugDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.allergyType").value("Non-Drug"))
                .andExpect(jsonPath("$.allergyName").value("Peanuts"));
    }

    @Test
    void shouldUpdateAllergy_whenExists() throws Exception {
        when(allergyService.updateAllergy(eq(1L), any())).thenReturn(dto);

        mockMvc.perform(put("/allergies/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.allergyType").value("Drug"))
                .andExpect(jsonPath("$.drugName").value("Penicillin"));
    }

    @Test
    void shouldReturnNotFound_whenUpdatingNonExistentAllergy() throws Exception {
        when(allergyService.updateAllergy(eq(99L), any()))
                .thenThrow(new RuntimeException("Allergy not found"));

        mockMvc.perform(put("/allergies/99")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldDeleteAllergy_whenExists() throws Exception {
        doNothing().when(allergyService).deleteAllergy(1L);

        mockMvc.perform(delete("/allergies/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldReturnNotFound_whenDeletingNonExistentAllergy() throws Exception {
        Mockito.doThrow(new RuntimeException("Allergy not found"))
                .when(allergyService).deleteAllergy(99L);

        mockMvc.perform(delete("/allergies/99"))
                .andExpect(status().isNotFound());
    }
} 