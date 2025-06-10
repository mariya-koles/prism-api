package com.platform.prism.model;

import org.junit.jupiter.api.Test;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class AllergyTest {

    private final Validator validator;

    public AllergyTest() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        this.validator = factory.getValidator();
    }

    @Test
    void testAllergyBuilderAndFields() {
        Allergy allergy = Allergy.builder()
                .id(1L)
                .allergyType("Drug")
                .drugName("Penicillin")
                .allergyName(null)
                .build();

        assertThat(allergy.getAllergyType()).isEqualTo("Drug");
        assertThat(allergy.getDrugName()).isEqualTo("Penicillin");
        assertThat(allergy.getAllergyName()).isNull();
    }

    @Test
    void testNonDrugAllergyBuilderAndFields() {
        Allergy allergy = Allergy.builder()
                .id(2L)
                .allergyType("Non-Drug")
                .allergyName("Shellfish")
                .drugName(null)
                .build();

        assertThat(allergy.getAllergyType()).isEqualTo("Non-Drug");
        assertThat(allergy.getAllergyName()).isEqualTo("Shellfish");
        assertThat(allergy.getDrugName()).isNull();
    }

    @Test
    void testAllergyValidationFailsOnMissingAllergyType() {
        Allergy allergy = Allergy.builder()
                .drugName("Penicillin")
                .build(); // missing allergyType

        Set<ConstraintViolation<Allergy>> violations = validator.validate(allergy);

        assertThat(violations).hasSizeGreaterThanOrEqualTo(1);
        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("allergyType"));
    }
} 