package com.platform.prism.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.io.Serializable;

/**
 * DTO for {@link com.platform.prism.model.Medication}
 */
@Schema(description = "Data Transfer Object for Medications")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class MedicationDto implements Serializable {

    @Schema(description = "Medication ID", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @NotBlank(message = "Proprietary name is required")
    @Schema(description = "Proprietary name of the medication", example = "Children's Tylenol")
    private String proprietaryName;

    @NotBlank(message = "NDC package code is required")
    @Schema(description = "NDC package code", example = "50580-139-04")
    private String ndcPackageCode;

    @Schema(description = "Strength of the medication", example = "160mg/5mL")
    private String strength;

    @Schema(description = "Dosage form", example = "SUSPENSION")
    private String dosageForm;

    @Schema(description = "Route of administration", example = "ORAL")
    private String route;

    @Schema(description = "Monograph ID", example = "M013")
    private String monographId;

    @Schema(description = "Labeler name", example = "Kenvue Brands LLC")
    private String labelerName;

    @Schema(description = "Product NDC", example = "50580-139")
    private String productNdc;

    @Schema(description = "Non-proprietary name", example = "Acetaminophen")
    private String nonProprietaryName;

    @Schema(description = "Substance name", example = "ACETAMINOPHEN")
    private String substanceName;

    @Schema(description = "Product type name", example = "HUMAN OTC DRUG")
    private String productTypeName;
}
