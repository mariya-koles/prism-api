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
 * DTO for Cardiac Consultation details
 */
@Schema(description = "Data Transfer Object for Cardiac Consultations")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class CardiacConsultationDto implements Serializable {
    @Schema(description="Chest pain rating (1-10)", example="4")
    private Integer chestPainScale;

    @Schema(description="Lipid panel (JSON)", example="{\"ldl\":100,\"hdl\":50}")
    private String lipidPanel;

    @Schema(description="Recent stress test result", example="Normal exercise tolerance")
    private String stressTestResult;

    @Schema(description="EKG summary", example="Sinus rhythm, no ST changes")
    private String ekgSummary;
}

