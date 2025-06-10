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
 * DTO for Stress Management Consultation details
 */
@Schema(description = "Data Transfer Object for Stress Management Consultations")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class StressManagementConsultationDto implements Serializable {

    @Schema(description = "Self-reported stress score (1-10)", example = "7")
    private Integer stressScore;

    @Schema(description = "Identified stressors", example = "Work deadlines")
    private String stressors;

    @Schema(description = "Recommended coping strategies", example = "Mindfulness, exercise")
    private String copingStrategies;
}
