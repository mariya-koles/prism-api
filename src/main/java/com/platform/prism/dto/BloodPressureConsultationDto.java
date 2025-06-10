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
 * DTO for Blood Pressure Consultation details
 */
@Schema(description = "Data Transfer Object for Blood pressure Consultations")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class BloodPressureConsultationDto implements Serializable {
    @Schema(description="Systolic pressure", example="130")
    private Integer systolic;

    @Schema(description="Diastolic pressure", example="85")
    private Integer diastolic;

    @Schema(description="Heart rate (bpm)", example="72")
    private Integer heartRate;

    @Schema(description="Medication adherence notes", example="Patient reported missing 2 doses/week")
    private String adherenceNotes;
}

