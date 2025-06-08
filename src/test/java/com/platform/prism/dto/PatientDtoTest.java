package com.platform.prism.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class PatientDtoTest {

    private static Validator validator;

    @BeforeAll
    static void setUpValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void shouldPassValidation_whenAllFieldsAreValid() {
        PatientDto dto = PatientDto.builder()
                .firstName("Jane")
                .lastName("Doe")
                .age(40)
                .dateOfBirth(LocalDate.of(1983, 4, 20))
                .email("jane.doe@example.com")
                .build();

        Set<ConstraintViolation<PatientDto>> violations = validator.validate(dto);
        assertThat(violations).isEmpty();
    }

    @Test
    void shouldFailValidation_whenRequiredFieldsAreMissing() {
        PatientDto dto = new PatientDto(); // all null

        Set<ConstraintViolation<PatientDto>> violations = validator.validate(dto);

        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("firstName"));
        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("lastName"));
        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("dateOfBirth"));
    }

    @Test
    void shouldFailValidation_whenAgeIsNegative() {
        PatientDto dto = PatientDto.builder()
                .firstName("Joe")
                .lastName("Negative")
                .dateOfBirth(LocalDate.of(1990, 1, 1))
                .age(-1)
                .build();

        Set<ConstraintViolation<PatientDto>> violations = validator.validate(dto);

        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("age"));
    }

    @Test
    void shouldFailValidation_whenEmailIsInvalid() {
        PatientDto dto = PatientDto.builder()
                .firstName("Bad")
                .lastName("Email")
                .dateOfBirth(LocalDate.of(2000, 1, 1))
                .email("not-an-email")
                .build();

        Set<ConstraintViolation<PatientDto>> violations = validator.validate(dto);

        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("email"));
    }

    @Test
    void builderShouldCreateValidDto() {
        PatientDto dto = PatientDto.builder()
                .firstName("Builder")
                .lastName("Test")
                .dateOfBirth(LocalDate.of(1995, 12, 31))
                .build();

        assertThat(dto.getFirstName()).isEqualTo("Builder");
        assertThat(dto.getLastName()).isEqualTo("Test");
        assertThat(dto.getDateOfBirth()).isEqualTo(LocalDate.of(1995, 12, 31));
    }
}
