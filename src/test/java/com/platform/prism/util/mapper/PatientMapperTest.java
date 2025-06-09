package com.platform.prism.util.mapper;

import com.platform.prism.dto.PatientDto;
import com.platform.prism.model.Patient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class PatientMapperTest {

    @Autowired
    private PatientMapper mapper;

    @Test
    void shouldMapPatientToDto() {
        Patient patient = new Patient();
        patient.setId(1L);
        patient.setFirstName("Alice");
        patient.setLastName("Smith");
        patient.setDateOfBirth(LocalDate.of(1990, 1, 1));

        PatientDto dto = mapper.toDto(patient);

        assertThat(dto.getId()).isEqualTo(1L);
        assertThat(dto.getFirstName()).isEqualTo("Alice");
        assertThat(dto.getLastName()).isEqualTo("Smith");
        assertThat(dto.getDateOfBirth()).isEqualTo(LocalDate.of(1990, 1, 1));
    }

    @Test
    void shouldMapDtoToPatient() {
        PatientDto dto = new PatientDto();
        dto.setId(2L);
        dto.setFirstName("Bob");
        dto.setLastName("Jones");
        dto.setDateOfBirth(LocalDate.of(1985, 5, 20));

        Patient patient = mapper.toEntity(dto);

        assertThat(patient.getId()).isEqualTo(2L);
        assertThat(patient.getFirstName()).isEqualTo("Bob");
        assertThat(patient.getLastName()).isEqualTo("Jones");
        assertThat(patient.getDateOfBirth()).isEqualTo(LocalDate.of(1985, 5, 20));
    }
}
