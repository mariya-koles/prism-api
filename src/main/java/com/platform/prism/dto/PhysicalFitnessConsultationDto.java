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
 * DTO for Physical Fitness Consultation details
 */
@Schema(description = "Data Transfer Object for Physical Fitness Consultations")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class PhysicalFitnessConsultationDto implements Serializable {

    @Schema(description = "Type of exercise prescribed", example = "Cardio + strength")
    private String exerciseType;

    @Schema(description = "Frequency per week", example = "4")
    private Integer frequencyPerWeek;

    @Schema(description = "Duration per session in minutes", example = "45")
    private Integer sessionDuration;

    @Schema(description = "Intensity level of exercise", example = "Moderate")
    private String intensity;
}
