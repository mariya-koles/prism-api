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
 * DTO for Men's Health Consultation details
 */
@Schema(description = "Data Transfer Object for Men's Health Consultations")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class MensHealthConsultationDto implements Serializable {

    @Schema(description = "Prostate screening completed", example = "true")
    private Boolean prostateScreeningDone;

    @Schema(description = "Testosterone level", example = "550")
    private BigDecimal testosteroneLevel;

    @Schema(description = "General review notes", example = "Low energy, libido issues")
    private String reviewNotes;
}
