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
 * DTO for {@link com.platform.prism.model.Allergy}
 */
@Schema(description = "Data Transfer Object for Allergens")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class AllergyDto implements Serializable {

    @Schema(description = "Allergy ID", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @NotBlank(message = "Allergy type is required")
    @Schema(description = "Type of allergy (Drug or Non-Drug", example = "Drug")
    private String allergyType;


    @Schema(description = "Name of the allergen", example = "Shellfish")
    private String allergyName;

    @Schema(description = "Name of the drug", example = "Paracetamol")
    private String drugName;
}
