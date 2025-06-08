package com.platform.prism.model;

import jakarta.persistence.EntityManager;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class PatientTest {

    @Autowired
    private EntityManager entityManager;

    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    void shouldPersistValidPatient() {
        Patient patient = Patient.builder()
                .firstName("Alice")
                .lastName("Johnson")
                .age(30)
                .dateOfBirth(LocalDate.of(1993, 5, 12))
                .email("alice@example.com")
                .phoneNumber("1234567890")
                .mobileNumber("0987654321")
                .streetAddress("123 Main St")
                .city("Austin")
                .state("TX")
                .zipcode("78701")
                .notes("Test note")
                .build();

        entityManager.persist(patient);
        entityManager.flush(); // Forces DB write

        assertThat(patient.getId()).isNotNull();
    }

    @Test
    void shouldFailValidationWhenRequiredFieldsMissing() {
        Patient patient = new Patient(); // missing everything

        Set<ConstraintViolation<Patient>> violations = validator.validate(patient);
        assertThat(violations).isNotEmpty();
        assertThat(violations)
                .anyMatch(v -> v.getPropertyPath().toString().equals("firstName"))
                .anyMatch(v -> v.getPropertyPath().toString().equals("lastName"))
                .anyMatch(v -> v.getPropertyPath().toString().equals("dateOfBirth"));
    }

    @Test
    void shouldFailWhenEmailIsInvalid() {
        Patient patient = Patient.builder()
                .firstName("Test")
                .lastName("User")
                .dateOfBirth(LocalDate.of(2000, 1, 1))
                .email("invalid-email")
                .build();

        Set<ConstraintViolation<Patient>> violations = validator.validate(patient);
        assertThat(violations)
                .anyMatch(v -> v.getPropertyPath().toString().equals("email"));
    }

    @Test
    void builderShouldWorkCorrectly() {
        Patient patient = Patient.builder()
                .firstName("Test")
                .lastName("Builder")
                .dateOfBirth(LocalDate.of(1995, 12, 31))
                .build();

        assertThat(patient.getFirstName()).isEqualTo("Test");
        assertThat(patient.getLastName()).isEqualTo("Builder");
    }
}
