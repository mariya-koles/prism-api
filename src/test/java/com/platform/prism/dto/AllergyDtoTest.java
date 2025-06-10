package com.platform.prism.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class AllergyDtoTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void shouldCreateValidDto_whenUsingBuilder() {
        AllergyDto dto = AllergyDto.builder()
                .id(1L)
                .allergyType("Drug")
                .drugName("Penicillin")
                .allergyName(null)
                .build();

        assertThat(dto.getId()).isEqualTo(1L);
        assertThat(dto.getAllergyType()).isEqualTo("Drug");
        assertThat(dto.getDrugName()).isEqualTo("Penicillin");
        assertThat(dto.getAllergyName()).isNull();
    }

    @Test
    void shouldCreateValidNonDrugDto_whenUsingBuilder() {
        AllergyDto dto = AllergyDto.builder()
                .id(2L)
                .allergyType("Non-Drug")
                .allergyName("Shellfish")
                .drugName(null)
                .build();

        assertThat(dto.getId()).isEqualTo(2L);
        assertThat(dto.getAllergyType()).isEqualTo("Non-Drug");
        assertThat(dto.getAllergyName()).isEqualTo("Shellfish");
        assertThat(dto.getDrugName()).isNull();
    }

    @Test
    void shouldBeEqual_whenFieldsAreIdentical() {
        AllergyDto dto1 = AllergyDto.builder()
                .id(1L)
                .allergyType("Drug")
                .drugName("Penicillin")
                .build();

        AllergyDto dto2 = AllergyDto.builder()
                .id(1L)
                .allergyType("Drug")
                .drugName("Penicillin")
                .build();

        assertThat(dto1).isEqualTo(dto2);
        assertThat(dto1.hashCode()).isEqualTo(dto2.hashCode());
    }

    @Test
    void shouldFailValidation_whenRequiredFieldsAreMissing() {
        AllergyDto invalidDto = new AllergyDto();

        Set<ConstraintViolation<AllergyDto>> violations = validator.validate(invalidDto);

        assertThat(violations).hasSizeGreaterThanOrEqualTo(1);
        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("allergyType"));
    }

    @Test
    void shouldPassValidation_whenRequiredFieldsArePresent() {
        AllergyDto validDto = AllergyDto.builder()
                .allergyType("Drug")
                .drugName("Aspirin")
                .build();

        Set<ConstraintViolation<AllergyDto>> violations = validator.validate(validDto);

        assertThat(violations).isEmpty();
    }

    @Test
    void shouldContainReadableInfo_whenToStringIsCalled() {
        AllergyDto dto = AllergyDto.builder()
                .allergyType("Non-Drug")
                .allergyName("Peanuts")
                .build();

        assertThat(dto.toString()).contains("Non-Drug", "Peanuts");
    }
} 