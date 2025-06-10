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
 * DTO for Weight Management Consultation details
 */
@Schema(description = "Data Transfer Object for Weight Management Consultations")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class WeightManagementConsultationDto implements Serializable {

    @Schema(description = "Initial weight in kg", example = "85.0")
    private BigDecimal initialWeightKg;

    @Schema(description = "Goal weight in kg", example = "75.0")
    private BigDecimal goalWeightKg;

    @Schema(description = "Change in weight in kg", example = "-2.5")
    private BigDecimal weightChangeKg;

    @Schema(description = "Diet and exercise plan", example = "1200 kcal/day + 30 min cardio")
    private String planDetails;
}
