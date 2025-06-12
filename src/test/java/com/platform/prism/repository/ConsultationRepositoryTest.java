package com.platform.prism.repository;

import com.platform.prism.enums.ConsultationType;
import com.platform.prism.model.Consultation;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class ConsultationRepositoryTest {

    @Autowired
    private ConsultationRepository consultationRepository;

    @Test
    @DisplayName("shouldSaveAndRetrieveConsultationById")
    void shouldSaveAndRetrieveConsultationById() {
        Consultation consultation = Consultation.builder()
                .patientId(1L)
                .consultationType(ConsultationType.ALLERGY_MANAGEMENT)
                .date(LocalDateTime.now())
                .appointmentAt(LocalDateTime.now().plusDays(1))
                .weightKg(new BigDecimal("70.5"))
                .bloodPressure("120/80")
                .notes("Initial consultation")
                .typeSpecificData("{\"newAllergens\":\"pollen\",\"reactionNotes\":\"sneezing\",\"treatmentPlan\":\"antihistamine\"}")
                .build();

        Consultation saved = consultationRepository.save(consultation);

        Optional<Consultation> retrieved = consultationRepository.findById(saved.getId());

        assertThat(retrieved).isPresent();
        assertThat(retrieved.get().getPatientId()).isEqualTo(1L);
        assertThat(retrieved.get().getConsultationType()).isEqualTo(ConsultationType.ALLERGY_MANAGEMENT);
        assertThat(retrieved.get().getTypeSpecificData()).isNotNull();
    }

    @Test
    @DisplayName("shouldReturnAllConsultations")
    void shouldReturnAllConsultations() {
        Consultation consultation1 = Consultation.builder()
                .patientId(1L)
                .consultationType(ConsultationType.ALLERGY_MANAGEMENT)
                .date(LocalDateTime.now())
                .appointmentAt(LocalDateTime.now().plusDays(1))
                .build();

        Consultation consultation2 = Consultation.builder()
                .patientId(2L)
                .consultationType(ConsultationType.NUTRITIONAL)
                .date(LocalDateTime.now())
                .appointmentAt(LocalDateTime.now().plusDays(1))
                .build();

        consultationRepository.saveAll(List.of(consultation1, consultation2));

        List<Consultation> consultations = consultationRepository.findAll();

        assertThat(consultations).hasSize(2);
        assertThat(consultations).extracting("consultationType")
                .contains(ConsultationType.ALLERGY_MANAGEMENT, ConsultationType.NUTRITIONAL);
    }

    @Test
    @DisplayName("shouldDeleteConsultation")
    void shouldDeleteConsultation() {
        Consultation consultation = Consultation.builder()
                .patientId(1L)
                .consultationType(ConsultationType.ALLERGY_MANAGEMENT)
                .date(LocalDateTime.now())
                .appointmentAt(LocalDateTime.now().plusDays(1))
                .build();

        Consultation saved = consultationRepository.save(consultation);
        Long id = saved.getId();

        consultationRepository.deleteById(id);

        Optional<Consultation> result = consultationRepository.findById(id);

        assertThat(result).isNotPresent();
    }
} 