package com.platform.prism.repository;

import com.platform.prism.model.Patient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class PatientRepositoryTest {

    @Autowired
    private PatientRepository patientRepository;

    @Test
    void shouldSaveAndFindPatientById() {
        Patient patient = new Patient();
        patient.setFirstName("Alice");
        patient.setLastName("Johnson");
        patient.setDateOfBirth(LocalDate.of(1988, 4, 15));

        Patient saved = patientRepository.save(patient);

        Optional<Patient> found = patientRepository.findById(saved.getId());
        assertThat(found).isPresent();
        assertThat(found.get().getFirstName()).isEqualTo("Alice");
    }

    @Test
    void shouldFindAllPatients() {
        Patient p1 = new Patient();
        p1.setFirstName("Tom");
        p1.setLastName("Lee");
        p1.setDateOfBirth(LocalDate.of(1988, 4, 15));

        Patient p2 = new Patient();
        p2.setFirstName("Sara");
        p2.setLastName("Kim");
        p2.setDateOfBirth(LocalDate.of(1988, 4, 15));

        patientRepository.save(p1);
        patientRepository.save(p2);

        assertThat(patientRepository.findAll()).hasSize(2);
    }

    @Test
    void shouldDeletePatient() {
        Patient patient = new Patient();
        patient.setFirstName("Mark");
        patient.setLastName("Smith");
        patient.setDateOfBirth(LocalDate.of(1988, 4, 15));

        Patient saved = patientRepository.save(patient);
        Long id = saved.getId();

        patientRepository.deleteById(id);

        assertThat(patientRepository.findById(id)).isNotPresent();
    }
}
