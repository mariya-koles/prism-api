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
 * DTO for Nutritional Consultation details
 */
@Schema(description = "Data Transfer Object for Nutritional Consultations")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class NutritionalConsultationDto implements Serializable {

    @Schema(description = "24-hour food recall or diet log", example = "3 meals, 2 snacks per day")
    private String dietHistory;

    @Schema(description = "Body mass index", example = "22.5")
    private BigDecimal bmi;

    @Schema(description = "Body fat percentage", example = "18.2")
    private BigDecimal bodyFatPercent;

    @Schema(description = "Waist-to-hip ratio", example = "0.85")
    private BigDecimal waistHipRatio;

    @Schema(description = "Resting metabolic rate (kcal/day)", example = "1600")
    private BigDecimal restingMetabolicRate;

    @Schema(description = "Relevant lab markers as JSON string", example = "{\"vitaminD\":30,\"b12\":500}")
    private String labValues;
}
