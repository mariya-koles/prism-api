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
 * DTO for Thyroid Consultation details
 */
@Schema(description = "Data Transfer Object for Thyroid Consultations")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ThyroidConsultationDto implements Serializable {
    @Schema(description="TSH level", example="2.5")
    private BigDecimal tsh;

    @Schema(description="Free T4 level", example="1.1")
    private BigDecimal freeT4;

    @Schema(description="Symptoms noted", example="Fatigue, weight gain")
    private String symptoms;

    @Schema(description="Medication dosage change", example="Levothyroxine 50mcg → 75mcg")
    private String dosageAdjustment;
}

