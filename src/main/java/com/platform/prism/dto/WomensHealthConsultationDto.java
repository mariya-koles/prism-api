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
import java.time.LocalDate;

/**
 * DTO for Womens Health Consultation details
 */
@Schema(description = "Data Transfer Object for Women's Health Consultations")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class WomensHealthConsultationDto implements Serializable {
    @Schema(description="Menstrual cycle start date", example="2025-06-01")
    private LocalDate menstrualStartDate;

    @Schema(description="Menstrual cycle end date", example="2025-06-05")
    private LocalDate menstrualEndDate;

    @Schema(description="Pregnancy status", example="Not pregnant")
    private String pregnancyStatus;

    @Schema(description="Pap smear done", example="true")
    private Boolean papSmearDone;
}
