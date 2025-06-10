package com.platform.prism.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO for {@link com.platform.prism.model.Consultation}
 */
@Schema(description = "Data Transfer Object for Consultations")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ConsultationDto implements Serializable {

    @Schema(description = "Consultation ID", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @NotNull(message = "Patient ID is required")
    @Schema(description = "Patient ID", example = "123")
    private Long patientId;

    @NotNull(message = "Consultation type is required")
    @Schema(description = "Type of consultation", example = "WELLNESS")
    private com.yourpackage.model.ConsultationType consultationType;

    @Schema(description = "Date the consultation occurred", example = "2025-06-10T14:30:00")
    private LocalDateTime date;

    @Schema(description = "Scheduled appointment date and time", example = "2025-06-11T09:00:00")
    private LocalDateTime appointmentAt;

    @Schema(description = "Patient weight in kg", example = "72.5")
    private BigDecimal weightKg;

    @Schema(description = "Blood pressure reading", example = "120/80")
    private String bloodPressure;

    @Schema(description = "General notes about the consultation")
    private String notes;

    @Schema(description = "JSON containing type-specific fields")
    private String typeSpecificData;

    @Schema(description = "Record creation timestamp", accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime createdAt;

    @Schema(description = "Record update timestamp", accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime updatedAt;
}
