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
 * DTO for Diabetes Consultation details
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Schema(description = "DTO for Diabetes Consultations")
public class DiabetesConsultationDto implements Serializable {
    @Schema(description="HbA1c (%)", example="7.2")
    private BigDecimal hba1c;

    @Schema(description="Fasting glucose (mg/dL)", example="110")
    private BigDecimal fastingGlucose;

    @Schema(description="Insulin resistance index (HOMA-IR)", example="2.3")
    private BigDecimal homaIr;

    @Schema(description="Weight change (%) since last consult", example="-2.5")
    private BigDecimal weightChangePercent;

    @Schema(description="Medications adjusted (JSON)", example="[{\"med\":\"Metformin\",\"dose\":\"500mg\"}]")
    private String medicationAdjustments;
}

