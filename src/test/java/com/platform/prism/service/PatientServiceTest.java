package com.platform.prism.service;

import com.platform.prism.dto.PatientDto;
import com.platform.prism.model.Patient;
import com.platform.prism.repository.PatientRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class PatientServiceTest {

    @Autowired
    private PatientService patientService;

    @Autowired
    private PatientRepository patientRepository;

    private Patient existingPatient;

    @BeforeEach
    void setUp() {
        patientRepository.deleteAll();

        existingPatient = new Patient();
        existingPatient.setFirstName("Alice");
        existingPatient.setLastName("Smith");
        existingPatient.setDateOfBirth(LocalDate.of(1990, 1, 1));
        existingPatient.setMedications(new HashSet<>());
        existingPatient = patientRepository.save(existingPatient);
    }

    @Test
    void shouldGetAllPatients() {
        List<Patient> allPatients = patientService.getAllPatients();
        assertThat(allPatients).hasSize(1);
    }

    @Test
    void shouldGetPatientById() {
        PatientDto dto = patientService.getPatientById(existingPatient.getId());

        assertThat(dto).isNotNull();
        assertThat(dto.getFirstName()).isEqualTo("Alice");
        assertThat(dto.getLastName()).isEqualTo("Smith");
    }

    @Test
    void getPatientById_shouldThrow_ifNotFound() {
        assertThatThrownBy(() -> patientService.getPatientById(999L))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Patient not found");
    }

    @Test
    void shouldUpdatePatient() {
        PatientDto updateDto = new PatientDto();
        updateDto.setFirstName("Updated");
        updateDto.setLastName("Patient");
        updateDto.setDateOfBirth(LocalDate.of(1985, 5, 15));
        updateDto.setMedications(Set.of());

        PatientDto updated = patientService.updatePatient(existingPatient.getId(), updateDto);

        assertThat(updated.getFirstName()).isEqualTo("Updated");
        assertThat(updated.getLastName()).isEqualTo("Patient");

        Patient reloaded = patientRepository.findById(existingPatient.getId()).orElseThrow();
        assertThat(reloaded.getFirstName()).isEqualTo("Updated");
    }


    @Test
    void shouldSaveNewPatient() {
        Patient newPatient = new Patient();
        newPatient.setFirstName("Bob");
        newPatient.setLastName("Jones");
        newPatient.setDateOfBirth(LocalDate.of(1982, 3, 10));

        Patient saved = patientService.savePatient(newPatient);

        assertThat(saved.getId()).isNotNull();
        assertThat(patientRepository.findAll()).hasSize(2);
    }

    @Test
    void shouldDeletePatient() {
        patientService.deletePatient(existingPatient.getId());
        assertThat(patientRepository.findById(existingPatient.getId())).isEmpty();
    }

    @Test
    void deletePatient_shouldThrow_ifNotFound() {
        assertThatThrownBy(() -> patientService.deletePatient(999L))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Patient not found");
    }
}
