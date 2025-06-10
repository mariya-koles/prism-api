package com.platform.prism.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * DTO for Allergy Management Consultation details
 */
@Schema(description = "Data Transfer Object for Allergy Management Consultation details")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class AllergyManagementConsultationDto implements Serializable {

    @Schema(description = "New allergens identified as JSON", example = "[{\"allergen\":\"pollen\",\"severity\":\"moderate\"}]")
    private String newAllergens;

    @Schema(description = "Symptoms or reaction notes", example = "Sneezing, watery eyes")
    private String reactionNotes;

    @Schema(description = "Treatment or intervention details", example = "Prescribed antihistamine")
    private String treatmentPlan;
}
