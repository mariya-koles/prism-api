package com.platform.prism.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class MedicationDtoTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void shouldCreateValidDto_whenUsingBuilder() {
        MedicationDto dto = MedicationDto.builder()
                .id(1L)
                .proprietaryName("Tylenol")
                .ndcPackageCode("50580-139-04")
                .strength("160mg/5mL")
                .dosageForm("SUSPENSION")
                .route("ORAL")
                .monographId("M013")
                .labelerName("Kenvue Brands LLC")
                .productNdc("50580-139")
                .nonProprietaryName("Acetaminophen")
                .substanceName("ACETAMINOPHEN")
                .productTypeName("HUMAN OTC DRUG")
                .build();

        assertThat(dto.getId()).isEqualTo(1L);
        assertThat(dto.getProprietaryName()).isEqualTo("Tylenol");
        assertThat(dto.getProductTypeName()).isEqualTo("HUMAN OTC DRUG");
    }

    @Test
    void shouldBeEqual_whenFieldsAreIdentical() {
        MedicationDto dto1 = MedicationDto.builder()
                .id(1L)
                .proprietaryName("Tylenol")
                .ndcPackageCode("50580-139-04")
                .build();

        MedicationDto dto2 = MedicationDto.builder()
                .id(1L)
                .proprietaryName("Tylenol")
                .ndcPackageCode("50580-139-04")
                .build();

        assertThat(dto1).isEqualTo(dto2);
        assertThat(dto1.hashCode()).isEqualTo(dto2.hashCode());
    }

    @Test
    void shouldFailValidation_whenRequiredFieldsAreMissing() {
        MedicationDto invalidDto = new MedicationDto();

        Set<ConstraintViolation<MedicationDto>> violations = validator.validate(invalidDto);

        assertThat(violations).hasSizeGreaterThanOrEqualTo(2);
        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("proprietaryName"));
        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("ndcPackageCode"));
    }

    @Test
    void shouldPassValidation_whenRequiredFieldsArePresent() {
        MedicationDto validDto = MedicationDto.builder()
                .proprietaryName("Aspirin")
                .ndcPackageCode("12345-6789")
                .build();

        Set<ConstraintViolation<MedicationDto>> violations = validator.validate(validDto);

        assertThat(violations).isEmpty();
    }

    @Test
    void shouldContainReadableInfo_whenToStringIsCalled() {
        MedicationDto dto = MedicationDto.builder()
                .proprietaryName("Ibuprofen")
                .ndcPackageCode("00000-1111")
                .build();

        assertThat(dto.toString()).contains("Ibuprofen", "00000-1111");
    }
}
