package com.platform.prism.util.mapper;

import com.platform.prism.dto.PatientDto;
import com.platform.prism.model.Patient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class PatientMapperTest {

    private PatientMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = Mappers.getMapper(PatientMapper.class);
    }

    @Test
    void shouldMapDtoToPatient() {
        PatientDto dto = PatientDto.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .age(45)
                .dateOfBirth(LocalDate.of(1980, 5, 15))
                .email("john.doe@example.com")
                .city("Houston")
                .build();

        Patient entity = mapper.toEntity(dto);

        assertThat(entity.getFirstName()).isEqualTo("John");
        assertThat(entity.getLastName()).isEqualTo("Doe");
        assertThat(entity.getCity()).isEqualTo("Houston");
    }

    @Test
    void shouldMapPatientToDto() {
        Patient patient = Patient.builder()
                .id(2L)
                .firstName("Jane")
                .lastName("Smith")
                .dateOfBirth(LocalDate.of(1990, 1, 1))
                .email("jane.smith@example.com")
                .build();

        PatientDto dto = mapper.toDto(patient);

        assertThat(dto.getId()).isEqualTo(2L);
        assertThat(dto.getFirstName()).isEqualTo("Jane");
        assertThat(dto.getEmail()).isEqualTo("jane.smith@example.com");
    }

    @Test
    void shouldUpdatePatientFromDto() {
        Patient existing = Patient.builder()
                .id(3L)
                .firstName("OldFirst")
                .lastName("OldLast")
                .city("Dallas")
                .build();

        PatientDto dto = PatientDto.builder()
                .firstName("NewFirst")
                .lastName("NewLast")
                .build();

        mapper.updateEntityFromDto(dto, existing);

        assertThat(existing.getFirstName()).isEqualTo("NewFirst");
        assertThat(existing.getLastName()).isEqualTo("NewLast");
        assertThat(existing.getCity()).isEqualTo("Dallas"); // unchanged
    }

    @Test
    void shouldIgnoreNulls_whenUpdatingFromDto() {
        Patient existing = Patient.builder()
                .firstName("Preserved")
                .lastName("User")
                .email("test@example.com")
                .build();

        PatientDto dto = PatientDto.builder()
                .firstName(null)
                .email("updated@example.com")
                .build();

        mapper.updateEntityFromDto(dto, existing);

        assertThat(existing.getFirstName()).isEqualTo("Preserved"); // unchanged due to null
        assertThat(existing.getEmail()).isEqualTo("updated@example.com"); // updated
    }
}
