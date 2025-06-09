package com.platform.prism.model;

import org.junit.jupiter.api.Test;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;


import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class MedicationTest {

    private final Validator validator;

    public MedicationTest() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        this.validator = factory.getValidator();
    }

    @Test
    void testMedicationBuilderAndFields() {
        Medication med = Medication.builder()
                .id(1L)
                .proprietaryName("Children's Tylenol")
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

        assertThat(med.getProprietaryName()).isEqualTo("Children's Tylenol");
        assertThat(med.getProductNdc()).isEqualTo("50580-139");
    }

    @Test
    void testMedicationValidationFailsOnMissingRequiredFields() {
        Medication med = new Medication(); // missing required fields

        Set<ConstraintViolation<Medication>> violations = validator.validate(med);

        assertThat(violations).hasSizeGreaterThanOrEqualTo(2);
        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("proprietaryName"));
        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("ndcPackageCode"));
    }

}
