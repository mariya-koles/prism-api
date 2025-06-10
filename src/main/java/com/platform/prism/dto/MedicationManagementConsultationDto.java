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
 * DTO for Medication Management Consultation details
 */
@Schema(description = "Data Transfer Object for Medication Management Consultations")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class MedicationManagementConsultationDto implements Serializable {

    @Schema(description = "Current medications as JSON", example = "[{\"name\":\"Lisinopril\",\"dose\":\"10mg\"}]")
    private String currentMedications;

    @Schema(description = "Medication adherence issues", example = "Misses evening dose twice weekly")
    private String adherenceNotes;

    @Schema(description = "Drug interaction concerns", example = "Interaction between simvastatin and grapefruit")
    private String interactionWarnings;
}
