package com.platform.prism.util.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.platform.prism.dto.AllergyManagementConsultationDto;
import com.platform.prism.dto.ConsultationDto;
import com.platform.prism.enums.ConsultationType;
import com.platform.prism.model.Consultation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
class ConsultationMapperTest {

    @Autowired
    private ConsultationMapper consultationMapper;

    @Autowired
    private ObjectMapper objectMapper;

    private Consultation consultation;
    private ConsultationDto dto;
    private AllergyManagementConsultationDto allergyDto;

    @BeforeEach
    void setUp() throws Exception {
        LocalDateTime now = LocalDateTime.now();
        
        consultation = Consultation.builder()
                .id(1L)
                .patientId(1L)
                .consultationType(ConsultationType.ALLERGY_MANAGEMENT)
                .date(now)
                .appointmentAt(now.plusDays(1))
                .weightKg(new BigDecimal("70.5"))
                .bloodPressure("120/80")
                .notes("Initial consultation")
                .typeSpecificData("{\"newAllergens\":\"pollen\",\"reactionNotes\":\"sneezing\",\"treatmentPlan\":\"antihistamine\"}")
                .createdAt(now)
                .updatedAt(now)
                .build();

        dto = ConsultationDto.builder()
                .id(1L)
                .patientId(1L)
                .consultationType(ConsultationType.ALLERGY_MANAGEMENT)
                .date(now)
                .appointmentAt(now.plusDays(1))
                .weightKg(new BigDecimal("70.5"))
                .bloodPressure("120/80")
                .notes("Initial consultation")
                .typeSpecificData("{\"newAllergens\":\"pollen\",\"reactionNotes\":\"sneezing\",\"treatmentPlan\":\"antihistamine\"}")
                .createdAt(now)
                .updatedAt(now)
                .build();

        allergyDto = AllergyManagementConsultationDto.builder()
                .newAllergens("pollen")
                .reactionNotes("sneezing")
                .treatmentPlan("antihistamine")
                .build();
    }

    @Test
    void shouldMapEntityToDto() {
        ConsultationDto result = consultationMapper.toDto(consultation);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(consultation.getId());
        assertThat(result.getPatientId()).isEqualTo(consultation.getPatientId());
        assertThat(result.getConsultationType()).isEqualTo(consultation.getConsultationType());
        assertThat(result.getTypeSpecificData()).isNotNull();
    }

    @Test
    void shouldMapDtoToEntity() {
        Consultation result = consultationMapper.toEntity(dto);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(dto.getId());
        assertThat(result.getPatientId()).isEqualTo(dto.getPatientId());
        assertThat(result.getConsultationType()).isEqualTo(dto.getConsultationType());
        assertThat(result.getTypeSpecificData()).isNotNull();
    }

    @Test
    void shouldUpdateEntityFromDto() {
        Consultation existing = Consultation.builder().build();
        
        consultationMapper.updateEntityFromDto(dto, existing);

        assertThat(existing.getPatientId()).isEqualTo(dto.getPatientId());
        assertThat(existing.getConsultationType()).isEqualTo(dto.getConsultationType());
        assertThat(existing.getTypeSpecificData()).isNotNull();
    }
} 