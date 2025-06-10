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
 * DTO for Mental Health Consultation details
 */
@Schema(description = "Data Transfer Object for Mental Health Consultations")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class MentalHealthConsultationDto implements Serializable {
    @Schema(description="PHQ‑9 score", example="8")
    private Integer phq9Score;

    @Schema(description="GAD‑7 score", example="6")
    private Integer gad7Score;

    @Schema(description="Current stressors or life events", example="Work-related stress")
    private String stressors;

    @Schema(description="Medication or therapy notes", example="Started CBT sessions")
    private String treatmentPlan;
}

