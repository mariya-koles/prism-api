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
 * DTO for Immunization Consultation details
 */
@Schema(description = "Data Transfer Object for Immunization Consultations")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ImmunizationConsultationDto implements Serializable {
    @Schema(description="Vaccines administered (JSON)", example="[{\"vaccine\":\"Flu\",\"lot\":\"ABC123\"}]")
    private String vaccinesGiven;

    @Schema(description="Next scheduled immunization", example="COVID‑19 booster in 6 months")
    private String nextImmunization;

    @Schema(description="Adverse reaction observed", example="Mild arm soreness")
    private String adverseReactions;
}
