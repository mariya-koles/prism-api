package com.platform.prism.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
@Table(name = "medication")
@Schema(description = "Medication model representing prescription or over-the-counter drugs")
public class Medication {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "proprietary_name", nullable = false, length = 255)
    @NotBlank(message = "Proprietary name is required")
    private String proprietaryName;

    @Column(name = "ndc_package_code", nullable = false, length = 50)
    @NotBlank(message = "NDC package code is required")
    private String ndcPackageCode;

    @Column(name = "strength", length = 50)
    private String strength;

    @Column(name = "dosage_form", length = 100)
    private String dosageForm;

    @Column(name = "route", length = 50)
    private String route;

    @Column(name = "monograph_id", length = 50)
    private String monographId;

    @Column(name = "labeler_name", length = 255)
    private String labelerName;

    @Column(name = "product_ndc", length = 50)
    private String productNdc;

    @Column(name = "non_proprietary_name", length = 255)
    private String nonProprietaryName;

    @Column(name = "substance_name", length = 255)
    private String substanceName;

    @Column(name = "product_type_name", length = 100)
    private String productTypeName;

    @ManyToMany(mappedBy = "medications")
    private Set<Patient> patients = new HashSet<>();
}
