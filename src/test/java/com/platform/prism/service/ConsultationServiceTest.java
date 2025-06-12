package com.platform.prism.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.platform.prism.dto.ConsultationDto;
import com.platform.prism.dto.AllergyManagementConsultationDto;
import com.platform.prism.model.Consultation;
import com.platform.prism.repository.ConsultationRepository;
import com.platform.prism.util.mapper.ConsultationMapper;
import com.platform.prism.enums.ConsultationType;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class ConsultationServiceTest {

    private ConsultationRepository consultationRepository;
    private ConsultationMapper consultationMapper;
    private ObjectMapper objectMapper;
    private ConsultationService consultationService;

    @BeforeEach
    void setUp() {
        consultationRepository = mock(ConsultationRepository.class);
        consultationMapper = Mappers.getMapper(ConsultationMapper.class); // use real mapper
        objectMapper = new ObjectMapper();
        consultationService = new ConsultationService(consultationRepository, consultationMapper, objectMapper);
    }

    @Test
    void shouldReturnAllConsultations() {
        Consultation consultation1 = Consultation.builder()
                .consultationType(ConsultationType.ALLERGY_MANAGEMENT)
                .patientId(1L)
                .date(LocalDateTime.now())
                .appointmentAt(LocalDateTime.now())
                .build();
        Consultation consultation2 = Consultation.builder()
                .consultationType(ConsultationType.WELLNESS)
                .patientId(2L)
                .date(LocalDateTime.now())
                .appointmentAt(LocalDateTime.now())
                .build();
        when(consultationRepository.findAll()).thenReturn(Arrays.asList(consultation1, consultation2));

        List<Consultation> result = consultationService.getAllConsultations();

        assertThat(result).hasSize(2).extracting("consultationType").contains(ConsultationType.ALLERGY_MANAGEMENT, ConsultationType.WELLNESS);
    }

    @Test
    void shouldReturnConsultationDto_whenIdExists() {
        Consultation consultation = Consultation.builder()
                .id(1L)
                .consultationType(ConsultationType.ALLERGY_MANAGEMENT)
                .patientId(1L)
                .date(LocalDateTime.now())
                .appointmentAt(LocalDateTime.now())
                .build();
        when(consultationRepository.findById(1L)).thenReturn(Optional.of(consultation));

        ConsultationDto dto = consultationService.getConsultationById(1L);

        assertThat(dto).isNotNull();
        assertThat(dto.getConsultationType()).isEqualTo(ConsultationType.ALLERGY_MANAGEMENT);
        assertThat(dto.getPatientId()).isEqualTo(1L);
    }

    @Test
    void shouldThrowException_whenConsultationNotFoundById() {
        when(consultationRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> consultationService.getConsultationById(99L))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Consultation not found");
    }

    @Test
    void shouldUpdateConsultation_whenIdExists() {
        Consultation existing = Consultation.builder()
                .id(1L)
                .consultationType(ConsultationType.ALLERGY_MANAGEMENT)
                .patientId(1L)
                .date(LocalDateTime.now())
                .appointmentAt(LocalDateTime.now())
                .build();

        ConsultationDto incoming = ConsultationDto.builder()
                .consultationType(ConsultationType.ALLERGY_MANAGEMENT)
                .patientId(1L)
                .date(LocalDateTime.now())
                .appointmentAt(LocalDateTime.now())
                .build();

        when(consultationRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(consultationRepository.save(any(Consultation.class))).thenAnswer(i -> i.getArgument(0));

        ConsultationDto updated = consultationService.updateConsultation(1L, incoming);

        assertThat(updated.getConsultationType()).isEqualTo(ConsultationType.ALLERGY_MANAGEMENT);
        assertThat(updated.getPatientId()).isEqualTo(1L);
    }

    @Test
    void shouldSaveConsultation() {
        Consultation newConsultation = Consultation.builder()
                .consultationType(ConsultationType.ALLERGY_MANAGEMENT)
                .patientId(1L)
                .date(LocalDateTime.now())
                .appointmentAt(LocalDateTime.now())
                .build();

        when(consultationRepository.save(newConsultation)).thenReturn(newConsultation);

        Consultation saved = consultationService.saveConsultation(newConsultation);

        assertThat(saved).isNotNull();
        assertThat(saved.getConsultationType()).isEqualTo(ConsultationType.ALLERGY_MANAGEMENT);
    }

    @Test
    void shouldDeleteConsultation_whenIdExists() {
        when(consultationRepository.existsById(1L)).thenReturn(true);

        consultationService.deleteConsultation(1L);

        verify(consultationRepository, times(1)).deleteById(1L);
    }

    @Test
    void shouldThrowException_whenDeletingNonExistentConsultation() {
        when(consultationRepository.existsById(99L)).thenReturn(false);

        assertThatThrownBy(() -> consultationService.deleteConsultation(99L))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Consultation not found");
    }

    @Test
    void shouldCreateConsultation() {
        ConsultationDto dto = ConsultationDto.builder()
                .consultationType(ConsultationType.ALLERGY_MANAGEMENT)
                .patientId(1L)
                .date(LocalDateTime.now())
                .appointmentAt(LocalDateTime.now())
                .build();

        Consultation entity = Consultation.builder()
                .consultationType(ConsultationType.ALLERGY_MANAGEMENT)
                .patientId(1L)
                .date(LocalDateTime.now())
                .appointmentAt(LocalDateTime.now())
                .build();

        when(consultationRepository.save(any(Consultation.class))).thenReturn(entity);

        ConsultationDto created = consultationService.createConsultation(dto);

        assertThat(created).isNotNull();
        assertThat(created.getConsultationType()).isEqualTo(ConsultationType.ALLERGY_MANAGEMENT);
        assertThat(created.getPatientId()).isEqualTo(1L);
    }
} 