package com.platform.prism.service;

import com.platform.prism.dto.AllergyDto;
import com.platform.prism.model.Allergy;
import com.platform.prism.repository.AllergyRepository;
import com.platform.prism.util.mapper.AllergyMapper;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class AllergyServiceTest {

    private AllergyRepository allergyRepository;
    private AllergyMapper allergyMapper;
    private AllergyService allergyService;

    @BeforeEach
    void setUp() {
        allergyRepository = mock(AllergyRepository.class);
        allergyMapper = Mappers.getMapper(AllergyMapper.class); // use real mapper
        allergyService = new AllergyService(allergyRepository, allergyMapper);
    }

    @Test
    void shouldReturnAllAllergies() {
        Allergy allergy1 = Allergy.builder().allergyType("Drug").drugName("Penicillin").build();
        Allergy allergy2 = Allergy.builder().allergyType("Non-Drug").allergyName("Shellfish").build();
        when(allergyRepository.findAll()).thenReturn(Arrays.asList(allergy1, allergy2));

        List<Allergy> result = allergyService.getAllAllergies();

        assertThat(result).hasSize(2).extracting("allergyType").contains("Drug", "Non-Drug");
    }

    @Test
    void shouldReturnAllergyDto_whenIdExists() {
        Allergy allergy = Allergy.builder()
                .id(1L)
                .allergyType("Drug")
                .drugName("Penicillin")
                .build();
        when(allergyRepository.findById(1L)).thenReturn(Optional.of(allergy));

        AllergyDto dto = allergyService.getAllergyById(1L);

        assertThat(dto).isNotNull();
        assertThat(dto.getDrugName()).isEqualTo("Penicillin");
        assertThat(dto.getAllergyType()).isEqualTo("Drug");
    }

    @Test
    void shouldThrowException_whenAllergyNotFoundById() {
        when(allergyRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> allergyService.getAllergyById(99L))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Allergy not found");
    }

    @Test
    void shouldUpdateAllergy_whenIdExists() {
        Allergy existing = Allergy.builder()
                .id(1L)
                .allergyType("Drug")
                .drugName("OldDrug")
                .build();

        AllergyDto incoming = AllergyDto.builder()
                .allergyType("Drug")
                .drugName("UpdatedDrug")
                .build();

        when(allergyRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(allergyRepository.save(any(Allergy.class))).thenAnswer(i -> i.getArgument(0));

        AllergyDto updated = allergyService.updateAllergy(1L, incoming);

        assertThat(updated.getDrugName()).isEqualTo("UpdatedDrug");
        assertThat(updated.getAllergyType()).isEqualTo("Drug");
    }

    @Test
    void shouldSaveAllergy() {
        Allergy newAllergy = Allergy.builder().allergyType("Non-Drug").allergyName("Peanuts").build();

        when(allergyRepository.save(newAllergy)).thenReturn(newAllergy);

        Allergy saved = allergyService.saveAllergy(newAllergy);

        assertThat(saved).isNotNull();
        assertThat(saved.getAllergyName()).isEqualTo("Peanuts");
    }

    @Test
    void shouldDeleteAllergy_whenIdExists() {
        when(allergyRepository.existsById(1L)).thenReturn(true);

        allergyService.deleteAllergy(1L);

        verify(allergyRepository, times(1)).deleteById(1L);
    }

    @Test
    void shouldThrowException_whenDeletingNonExistentAllergy() {
        when(allergyRepository.existsById(99L)).thenReturn(false);

        assertThatThrownBy(() -> allergyService.deleteAllergy(99L))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Allergy not found");
    }

    @Test
    void shouldUpdateNonDrugAllergy_whenIdExists() {
        Allergy existing = Allergy.builder()
                .id(2L)
                .allergyType("Non-Drug")
                .allergyName("OldAllergen")
                .build();

        AllergyDto incoming = AllergyDto.builder()
                .allergyType("Non-Drug")
                .allergyName("UpdatedAllergen")
                .build();

        when(allergyRepository.findById(2L)).thenReturn(Optional.of(existing));
        when(allergyRepository.save(any(Allergy.class))).thenAnswer(i -> i.getArgument(0));

        AllergyDto updated = allergyService.updateAllergy(2L, incoming);

        assertThat(updated.getAllergyName()).isEqualTo("UpdatedAllergen");
        assertThat(updated.getAllergyType()).isEqualTo("Non-Drug");
    }
} 