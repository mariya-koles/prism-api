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
 * DTO for Sleep Health Consultation details
 */
@Schema(description = "Data Transfer Object for Sleep Health Consultations")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class SleepHealthConsultationDto implements Serializable {

    @Schema(description = "Average sleep duration in hours", example = "7.0")
    private BigDecimal sleepHours;

    @Schema(description = "Sleep quality rating (1-5)", example = "4")
    private Integer sleepQuality;

    @Schema(description = "Noted sleep disturbances", example = "Waking at 3 AM")
    private String disturbances;

    @Schema(description = "Sleep hygiene recommendations", example = "Avoid screens 1hr before bed")
    private String hygieneRecommendations;
}
