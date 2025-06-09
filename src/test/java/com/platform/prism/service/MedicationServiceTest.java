package com.platform.prism.service;

import com.platform.prism.dto.MedicationDto;
import com.platform.prism.model.Medication;
import com.platform.prism.repository.MedicationRepository;
import com.platform.prism.util.mapper.MedicationMapper;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class MedicationServiceTest {

    private MedicationRepository medicationRepository;
    private MedicationMapper medicationMapper;
    private MedicationService medicationService;

    @BeforeEach
    void setUp() {
        medicationRepository = mock(MedicationRepository.class);
        medicationMapper = Mappers.getMapper(MedicationMapper.class); // use real mapper
        medicationService = new MedicationService(medicationRepository, medicationMapper);
    }

    @Test
    void shouldReturnAllMedications() {
        Medication med1 = Medication.builder().proprietaryName("Tylenol").ndcPackageCode("111").build();
        Medication med2 = Medication.builder().proprietaryName("Aspirin").ndcPackageCode("222").build();
        when(medicationRepository.findAll()).thenReturn(Arrays.asList(med1, med2));

        List<Medication> result = medicationService.getAllMedications();

        assertThat(result).hasSize(2).extracting("proprietaryName").contains("Tylenol", "Aspirin");
    }

    @Test
    void shouldReturnMedicationDto_whenIdExists() {
        Medication med = Medication.builder()
                .id(1L)
                .proprietaryName("Tylenol")
                .ndcPackageCode("111")
                .build();
        when(medicationRepository.findById(1L)).thenReturn(Optional.of(med));

        MedicationDto dto = medicationService.getMedicationById(1L);

        assertThat(dto).isNotNull();
        assertThat(dto.getProprietaryName()).isEqualTo("Tylenol");
    }

    @Test
    void shouldThrowException_whenMedicationNotFoundById() {
        when(medicationRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> medicationService.getMedicationById(99L))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Medication not found");
    }

    @Test
    void shouldUpdateMedication_whenIdExists() {
        Medication existing = Medication.builder()
                .id(1L)
                .proprietaryName("OldName")
                .ndcPackageCode("123")
                .build();

        MedicationDto incoming = MedicationDto.builder()
                .proprietaryName("UpdatedName")
                .ndcPackageCode("456")
                .build();

        when(medicationRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(medicationRepository.save(any(Medication.class))).thenAnswer(i -> i.getArgument(0));

        MedicationDto updated = medicationService.updateMedication(1L, incoming);

        assertThat(updated.getProprietaryName()).isEqualTo("UpdatedName");
        assertThat(updated.getNdcPackageCode()).isEqualTo("456");
    }

    @Test
    void shouldSaveMedication() {
        Medication newMed = Medication.builder().proprietaryName("Ibuprofen").ndcPackageCode("999").build();

        when(medicationRepository.save(newMed)).thenReturn(newMed);

        Medication saved = medicationService.saveMedication(newMed);

        assertThat(saved).isNotNull();
        assertThat(saved.getProprietaryName()).isEqualTo("Ibuprofen");
    }

    @Test
    void shouldDeleteMedication_whenIdExists() {
        when(medicationRepository.existsById(1L)).thenReturn(true);

        medicationService.deleteMedication(1L);

        verify(medicationRepository, times(1)).deleteById(1L);
    }

    @Test
    void shouldThrowException_whenDeletingNonExistentMedication() {
        when(medicationRepository.existsById(99L)).thenReturn(false);

        assertThatThrownBy(() -> medicationService.deleteMedication(99L))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Medication not found");
    }
}
